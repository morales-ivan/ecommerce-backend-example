package com.sirius.Ecommerce.model.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryListingDTO {
    public Long id;
    public String name;
    public String description;

    public static CategoryListingDTO fromCategory(Category c) {
        return new CategoryListingDTO(c.getId(), c.getName(), c.getDescription());
    }

}
