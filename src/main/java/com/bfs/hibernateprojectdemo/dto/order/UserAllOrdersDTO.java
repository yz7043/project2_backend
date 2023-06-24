package com.bfs.hibernateprojectdemo.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAllOrdersDTO {
    List<UserOrderDTO> orders;
}
