package com.sirius.Ecommerce.model.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryCreationDTO {
    public String name;
    public String description;

    public static CategoryCreationDTO fromCategory(Category c) {
        return new CategoryCreationDTO(c.getName(), c.getDescription());
    }

}
