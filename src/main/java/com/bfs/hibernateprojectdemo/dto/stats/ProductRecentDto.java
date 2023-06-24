package com.bfs.hibernateprojectdemo.dto.stats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductRecentDto {
    private Long productId;
    private String name;
    private String description;
    private LocalDateTime datePurchased;
}
