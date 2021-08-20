package com.sirius.Ecommerce.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCreationDTO {
    public String fullName;
    public String username;
    public String password;
    public Set<Long> authorityIds;
}
