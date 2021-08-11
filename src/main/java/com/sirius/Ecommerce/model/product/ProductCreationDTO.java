package com.sirius.Ecommerce.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductCreationDTO {
    public String name;
    public String description;
    public Long quantity;
    public Set<Long> categoryIds;

    public static ProductCreationDTO fromProduct(Product p) {
        Set<Long> categoryIds = p.getCategories().stream().map(category -> category.getId()).collect(Collectors.toSet());
        return new ProductCreationDTO(p.getName(), p.getDescription(), p.getQuantity(), categoryIds);
    }

}
