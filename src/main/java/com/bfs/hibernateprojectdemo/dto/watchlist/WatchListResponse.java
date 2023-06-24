package com.bfs.hibernateprojectdemo.dto.watchlist;

import com.bfs.hibernateprojectdemo.dto.common.StatusResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WatchListResponse {
    StatusResponse status;
    List<WatchListItem> products;
}
