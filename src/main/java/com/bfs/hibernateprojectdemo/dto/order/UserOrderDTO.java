package com.bfs.hibernateprojectdemo.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrderDTO {
    private Long orderId;
    private LocalDateTime datePlaced;
    private String status;
    private String username;
    private Long userId;
}
