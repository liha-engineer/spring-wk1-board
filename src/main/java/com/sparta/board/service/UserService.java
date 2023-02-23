package com.sparta.board.service;

import com.sparta.board.dto.LoginRequestDto;
import com.sparta.board.dto.SignupRequestDto;
import com.sparta.board.dto.SuccessResponseDto;
import com.sparta.board.entity.User;
import com.sparta.board.entity.UserRoleEnum;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC"; //관리자토큰

    public SuccessResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            return new SuccessResponseDto("이미 존재하는 username 입니다.", HttpStatus.BAD_REQUEST.value());
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 일치하지 않습니다.");
            } else {
                role = UserRoleEnum.ADMIN;
            }
        }
        User user = new User(signupRequestDto);
        user.setRole(role);
        userRepository.save(user);
        return new SuccessResponseDto("회원가입 성공", HttpStatus.OK.value());
    }

    @Transactional(readOnly = true)
    public SuccessResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();
        // 사용자 확인 - 이미 등록되어 있는지
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
        // 아이디나 비밀번호가 일치하지 않으면 로그인 할수 없게 만들 것
        if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
            response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
            return new SuccessResponseDto("로그인 성공", HttpStatus.OK.value());
        }
            return new SuccessResponseDto("회원을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST.value());
    }
}
