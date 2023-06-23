package com.bfs.hibernateprojectdemo.domain;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "purchased_price", nullable = false)
    private Double purchasedPrice;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "wholesale_price", nullable = false)
    private Double wholesalePrice;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @ToString.Exclude
    private Product product;
}
