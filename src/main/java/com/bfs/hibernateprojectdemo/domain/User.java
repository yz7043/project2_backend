package com.bfs.hibernateprojectdemo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "role", nullable = false)
    private Integer role;

    @Column(name = "icon_url", length = 1000)
    private String iconUrl;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    @Builder.Default
    private List<Permission> permissions = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    @ManyToMany
    @ToString.Exclude
    @Builder.Default
    @JoinTable(name="watchlist", joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "product_id")})
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
        product.getUsers().add(this);
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.getUsers().remove(this);
    }
}
