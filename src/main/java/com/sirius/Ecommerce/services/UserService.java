package com.sirius.Ecommerce.services;

import com.sirius.Ecommerce.model.user.UserCreationDTO;
import com.sirius.Ecommerce.model.user.UserListingDTO;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    List<UserListingDTO> getUsers();

    Page<UserListingDTO> getPaginatedUsers(Integer pageSize, Integer pageNumber);

    UserListingDTO getUserById(Long userId);

    UserListingDTO save(UserCreationDTO userCreationDto);

    void updateUser(Long userId, UserCreationDTO userCreationDTO);

    void deleteUser(Long userId);

    boolean usernameExists(String username);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
