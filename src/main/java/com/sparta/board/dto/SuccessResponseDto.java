package com.sparta.board.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;


@Getter
public class SuccessResponseDto {
    private String msg;
    private int statusCode;

    @Builder
    public SuccessResponseDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
