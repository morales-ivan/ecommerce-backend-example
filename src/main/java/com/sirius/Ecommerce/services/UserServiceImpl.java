package com.sirius.Ecommerce.services;

import com.sirius.Ecommerce.model.role.Role;
import com.sirius.Ecommerce.model.user.User;
import com.sirius.Ecommerce.model.user.UserCreationDTO;
import com.sirius.Ecommerce.model.user.UserListingDTO;
import com.sirius.Ecommerce.repositories.RoleRepository;
import com.sirius.Ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private Long defaultRoleId = Long.valueOf(1);

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<UserListingDTO> getUsers() {
        List<User> users = new ArrayList<>((Collection<? extends User>) userRepository.findAll());
        return users.stream().map(UserListingDTO::fromUser).collect(Collectors.toList());
    }

    public List<User> getProperUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Page<UserListingDTO> getPaginatedUsers(Integer pageSize, Integer pageNumber) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<User> paginatedUsers = userRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return new PageImpl<>(paginatedUsers.getContent().stream()
                                    .map(UserListingDTO::fromUser).collect(Collectors.toList()),
                              pageRequest,
                              paginatedUsers.getTotalElements());
    }

    @Override
    public UserListingDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("User not found"));
        return UserListingDTO.fromUser(user);
    }

    @Override
    public UserListingDTO save(UserCreationDTO requestUser) {
        if (userRepository.existsByUsername(requestUser.getUsername()))
            throw new IllegalArgumentException("Existing username!");

        User user = new User();

        Set<Role> authorities;

        if (requestUser.getAuthorityIds() == null) {
            authorities = Stream.of(roleRepository.findById(defaultRoleId)
                    .orElseThrow(() -> new IllegalArgumentException("Role not found (invalid role id"))
            ).collect(Collectors.toSet());
        } else {
            authorities = Stream.concat(Stream.of(defaultRoleId), requestUser.getAuthorityIds().stream())
                    .map(authorityId -> roleRepository.findById(authorityId.longValue())
                            .orElseThrow(() -> new IllegalArgumentException("Role not found (invalid role id)")))
                    .collect(Collectors.toSet());
        }

        user.setFullName(requestUser.getFullName());
        user.setUsername(requestUser.getUsername());
        user.setPassword(passwordEncoder.encode(requestUser.getPassword()));
        user.setAuthorities(authorities);

        return UserListingDTO.fromUser(userRepository.save(user));
    }

    @Override
    public void updateUser(Long id, UserCreationDTO userCreationDto) {
        if (userRepository.existsByUsername(userCreationDto.getUsername())) {
            throw new IllegalArgumentException("Existing user name!");
        }

        User userFromDb = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        Set<Role> authorities;

        if (userCreationDto.getAuthorityIds() == null) {
            authorities = Stream.of(roleRepository.findById(defaultRoleId)
                    .orElseThrow(() -> new IllegalArgumentException("Role not found (invalid role id)"))
            ).collect(Collectors.toSet());
        } else {
            authorities = Stream.concat(Stream.of(defaultRoleId), userCreationDto.getAuthorityIds().stream())
                    .map(authorityId -> roleRepository.findById(authorityId.longValue())
                            .orElseThrow(() -> new IllegalArgumentException("Role not found (invalid role id)")))
                    .collect(Collectors.toSet());
        }

        userFromDb.setFullName(userCreationDto.getFullName());
        userFromDb.setUsername(userCreationDto.getUsername());
        userFromDb.setPassword(passwordEncoder.encode(userCreationDto.getPassword()));
        userFromDb.setAuthorities(authorities);

        userRepository.save(userFromDb);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(format("User with username - %s, not found", username))
                );
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}