package com.sirius.Ecommerce.model.user;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class UserAuthenticationDTO {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
