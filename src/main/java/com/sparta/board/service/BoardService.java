package com.sparta.board.service;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.dto.BooleanResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
// 이 부분은 서비스 부분이에요!
@RequiredArgsConstructor
// 뭔진 모르겠지만 얘는 인자값이 필요한 생성자인가보다
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Board createBoard(BoardRequestDto requestDto) {
        Board board = new Board(requestDto);
        boardRepository.save(board);
        return board;
    }

    @Transactional(readOnly = true)
    public List<Board> getBoards() {
        return boardRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional
    public BoardResponseDto getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 게시물이 존재하지 않아요!")
        );
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto update(Long id, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow (
                () -> new IllegalArgumentException("해당 아이디의 게시물이 존재하지 않아요!")
        );
        //비밀번호가 맞으면 수정, 아니면 수정 못하게 해야 하니까 비밀번호를 받아와야해
        if (!board.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
//            뭘 써야하지? ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ
//             오????
            }
        board.update(requestDto);
        return new BoardResponseDto(board);
    }

    @Transactional
    public BooleanResponseDto delete(Long id, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 게시물이 존재하지 않아요!")
        );
        if (!board.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        boardRepository.deleteById(id);
        return new BooleanResponseDto(true);
    }
}
