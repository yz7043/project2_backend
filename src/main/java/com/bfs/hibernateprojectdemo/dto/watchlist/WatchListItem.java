package com.bfs.hibernateprojectdemo.dto.watchlist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WatchListItem {
    private Long id; // product id
    private String description;
    private String name;
    private Integer quantity;
    private Double retailPrice;
}
