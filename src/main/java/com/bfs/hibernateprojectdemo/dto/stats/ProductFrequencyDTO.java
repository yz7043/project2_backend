package com.bfs.hibernateprojectdemo.dto.stats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductFrequencyDTO {
    private Long productId;
    private String name;
    private String description;
    private Integer frequency;
}
