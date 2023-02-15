package com.sparta.board.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;


@Getter
public class SuccessResponseDto {
    private String msg;
    private int statuscode;

    public SuccessResponseDto(String msg, int statuscode) {
        this.msg = msg;
        this.statuscode = statuscode;
    }
}
