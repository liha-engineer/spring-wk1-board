package com.sparta.board.dto;

import lombok.Getter;

@Getter
public class BooleanResponseDto {
    private boolean succeed;

    public BooleanResponseDto(boolean succeed) {
        this.succeed = succeed;
    }
}
