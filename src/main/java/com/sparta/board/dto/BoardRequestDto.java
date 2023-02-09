package com.sparta.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class BoardRequestDto {
    private String title;
    private String username;
    private String contents;
    private String password;
}

