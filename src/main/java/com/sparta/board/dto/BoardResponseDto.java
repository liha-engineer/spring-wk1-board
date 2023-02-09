package com.sparta.board.dto;

import com.sparta.board.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private LocalDateTime createdAt;
    private Long id;
    private String title;
    private String username;
    private String contents;

    public BoardResponseDto(Board board) {
        this.createdAt = board.getCreatedAt();
        this.id = board.getId();
        this.title = board.getTitle();
        this.username = board.getUsername();
        this.contents = board.getContents();
    }

}