package com.sirius.Ecommerce.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserListingDTO {
    public Long id;
    public String fullName;
    public String username;
    public Set<Long> authorityIds;

    public static UserListingDTO fromUser(User u) {
        Set<Long> authorityIds = u.getAuthorities().stream().map(role -> role.getId())
                .collect(Collectors.toSet());
        return new UserListingDTO(u.getId(), u.getFullName(), u.getUsername(), authorityIds);
    }
}
