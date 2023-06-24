package com.bfs.hibernateprojectdemo.dto.order.orderdetail;

import com.bfs.hibernateprojectdemo.dto.common.StatusResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDetailResponse {
    private StatusResponse status;
    // Order info
    private Long id; // order id
    private String orderStatus;
    private LocalDateTime datePlaced;
    private List<OrderItemDetailDTO> orderItems;
}
