package com.bfs.hibernateprojectdemo.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateProductRequest {
    @NotEmpty(message = "Product name can't be empty")
    @Size(max = 255, message = "Product name exceeds 255")
    private String name;

    @NotEmpty(message = "Product description name can't be empty")
    @Size(max = 255, message = "Product description exceeds 255")
    private String description;

    @NotNull(message = "Wholesale price can't be empty")
    @Positive(message = "Wholesale price should be positive")
    private Double wholesalePrice;

    @NotNull(message = "Retail price can't be empty")
    @Positive(message = "Retail price should be positive")
    private Double retailPrice;

    @NotNull(message = "Quantity can't be empty")
    @PositiveOrZero(message = "Quantity should be positive")
    private Integer quantity;
}
