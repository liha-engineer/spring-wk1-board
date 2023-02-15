package com.sparta.board.controller;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.dto.SuccessResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api/board")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
        return boardService.createBoard(requestDto, request);
    }

    @GetMapping("/api/boards")
    public List<Board> getBoards() {
        return boardService.getBoards();
    }

    @GetMapping("/api/board/{id}")
    public BoardResponseDto getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    @PutMapping("api/board/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
        return boardService.update(id, requestDto, request);
    }
    @DeleteMapping("api/board/{id}")
    public SuccessResponseDto deleteBoard(@PathVariable Long id, HttpServletRequest request) {
        return boardService.delete(id, request);
    }
}



