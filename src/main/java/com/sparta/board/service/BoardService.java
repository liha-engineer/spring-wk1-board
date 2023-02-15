package com.sparta.board.service;

import com.sparta.board.dto.*;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.User;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
// 이 부분은 서비스 부분이에요!
@RequiredArgsConstructor
// 뭔진 모르겠지만 얘는 인자값이 필요한 생성자인가보다
public class BoardService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    Claims claims;

    @Transactional
    public boolean userValidation(@RequestBody HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

        boolean valid = false;
        // 토큰검증해서 유효한 경우만 게시물 관련 작업 가능하게 하고싶음
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) { // 토큰에서 사용자 정보 가져오기 - 사용자가 존재하는지도 이미 여기서 검증!
                // 근데 이건 사용자 존재여부 검증임. 이 사용자가 게시글 작성자랑 같은지 확인하는건 별개 문제.
                claims = jwtUtil.getUserInfoFromToken(token);
            } else throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            // token에서 검사한 사용자와 게시글 작성자가 같은지 알고싶다면- token subject에 username 들어감. 이걸 가져오고 싶음.
            valid = claims.getSubject().equals(user.getUsername());
        }
        return valid;
    }

    @Transactional
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
        if (userValidation(request)) {
            Board board = new Board(requestDto);
            boardRepository.save(board);
            return new BoardResponseDto(board);
        } else throw new IllegalArgumentException("토큰이 없습니다.");
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
    public BoardResponseDto update(Long id, BoardRequestDto requestDto, HttpServletRequest request) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 게시물이 존재하지 않아요!")
        );
        // 토큰이 유효하고 && 작성글의 username과 requestDto의 username이 같다면 수정 시켜주기
        if (userValidation(request) && board.getUsername().equals(requestDto.getUsername())) {
            board.update(requestDto);
        }
        return new BoardResponseDto(board);
    }

    @Transactional
    public SuccessResponseDto delete(Long id, HttpServletRequest request) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디의 게시물이 존재하지 않아요!")
        ); // 토큰 sub에 username 들어가니까, 토큰에 있는 이름이 user DB에 있는지 검색.
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("해당하는 유저가 없습니다.")
        );
        System.out.println("board.getUsername()은: " + board.getUsername());
        System.out.println("user.getUsername()은: " + user.getUsername());

        if (userValidation(request) && board.getUsername().equals(claims.getSubject())) {
            boardRepository.deleteById(id);
            return new SuccessResponseDto("삭제 성공", HttpStatus.OK.value());
        } else {
            return new SuccessResponseDto("작업 실패", HttpStatus.BAD_REQUEST.value());
        }
    }
}
