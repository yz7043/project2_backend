package com.bfs.hibernateprojectdemo.dto.order.seller;

import com.bfs.hibernateprojectdemo.dto.order.UserOrderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PagedResponse {
    private int totalPages;
    private int currentPage;
    private List<UserOrderDTO> orders;
}
