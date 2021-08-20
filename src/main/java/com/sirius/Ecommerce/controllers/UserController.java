package com.sirius.Ecommerce.controllers;


import com.sirius.Ecommerce.model.user.User;
import com.sirius.Ecommerce.model.user.UserCreationDTO;
import com.sirius.Ecommerce.model.user.UserListingDTO;
import com.sirius.Ecommerce.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping
    public List<UserListingDTO> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{pageSize}/{pageNumber}")
    public Page<UserListingDTO> getPaginatedUsers(@PathVariable Integer pageSize, @PathVariable Integer pageNumber) {
        return userService.getPaginatedUsers(pageSize, pageNumber);
    }

    @GetMapping("/{userId}")
    public UserListingDTO getUser(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    public UserListingDTO saveUser(@RequestBody UserCreationDTO userCreationDto) {
        return userService.save(userCreationDto);
    }

    @PutMapping("/{userId}")
    public UserListingDTO updateUser(@PathVariable Long userId, @RequestBody UserCreationDTO userCreationDTO) {
        userService.updateUser(userId, userCreationDTO);
        return userService.getUserById(userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserListingDTO> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}