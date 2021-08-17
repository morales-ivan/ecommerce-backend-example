package com.sirius.Ecommerce.model.product;

import com.sirius.Ecommerce.model.category.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    Long id;
    @Column
    String name;
    @Column
    String description;
    @Column
    Long quantity;

    @ManyToMany
    Set<Category> categories = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime lastModified;

    public Product(Long productId, String name, String description, Set<Category> categories) {
        this.id = productId;
        this.name = name;
        this.description = description;
        this.categories = categories;
        // TODO posible rotura de los timestamps (se instancia con timesteamps vacios?) --> NOUP
    }
}
