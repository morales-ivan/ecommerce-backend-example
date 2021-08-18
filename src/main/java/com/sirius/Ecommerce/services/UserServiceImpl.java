package com.sirius.Ecommerce.services;

import com.sirius.Ecommerce.model.user.User;
import com.sirius.Ecommerce.model.user.UserCreationDTO;
import com.sirius.Ecommerce.model.user.UserListingDTO;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserListingDTO> getUsers() {
        List<User> users = new ArrayList<>((Collection<? extends User>) userRepository.findAll());
        return users.stream().map(UserListingDTO::fromUser).collect(Collectors.toList());
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
            throw new IllegalArgumentException("Existing user name!");

        User user = new User();
        user.setFullName(requestUser.getFullName());
        user.setUsername(requestUser.getUsername());
        user.setPassword(passwordEncoder.encode(requestUser.getPassword()));

        return UserListingDTO.fromUser(userRepository.save(user));
    }

    @Override
    public void updateUser(Long id, UserCreationDTO userCreationDto) {
        User userFromDb = userRepository.findById(id).get();
        userFromDb.setFullName(userCreationDto.getFullName());
        userFromDb.setUsername(userCreationDto.getUsername());
        userFromDb.setPassword(userCreationDto.getPassword());

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