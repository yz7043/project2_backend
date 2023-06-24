package com.bfs.hibernateprojectdemo.dto.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatusResponse {
    private Boolean success;
    private String message;
}
