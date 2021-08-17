package com.sirius.Ecommerce.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserListingDTO {
    public Long id;
    public String fullName;
    public String username;

    public static UserListingDTO fromUser(User u) {
        return new UserListingDTO(u.getId(), u.getFullName(), u.getUsername());
    }
}
