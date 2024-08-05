package com.example.likelion12.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchRequest {

    private int page;             // 현재 페이지 번호

    public int getOffset() {
        return (page - 1) * 9;
    }
}
