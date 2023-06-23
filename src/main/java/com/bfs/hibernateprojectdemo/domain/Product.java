package com.bfs.hibernateprojectdemo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "retail_price", nullable = false)
    private Double retailPrice;

    @Column(name = "wholesale_price", nullable = false)
    private Double wholesalePrice;

    @ManyToMany(mappedBy = "products")
    @ToString.Exclude
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
        user.getProducts().add(this);
    }

    public void removeUser(User user) {
        users.remove(user);
        user.getProducts().remove(this);
    }
}
