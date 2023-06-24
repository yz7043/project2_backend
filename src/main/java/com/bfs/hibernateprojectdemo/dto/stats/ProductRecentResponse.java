package com.bfs.hibernateprojectdemo.dto.stats;

import com.bfs.hibernateprojectdemo.dto.common.StatusResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductRecentResponse {
    private StatusResponse status;
    private List<ProductRecentDto> recentProducts;
}
