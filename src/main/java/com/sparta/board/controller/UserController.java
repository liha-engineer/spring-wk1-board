package com.sparta.board.controller;

import com.sparta.board.dto.LoginRequestDto;
import com.sparta.board.dto.SignupRequestDto;
import com.sparta.board.dto.SuccessResponseDto;
import com.sparta.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public ModelAndView signupPage() {
        return new ModelAndView("signup");
    }

    @GetMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    @PostMapping("/signup")
    public SuccessResponseDto signup(@RequestBody @Valid SignupRequestDto requestDto, Errors errors) {
        //SignupRequestDto 인자값 유효성 검사해서 문제있으면 if문 타게 만듬.
        if(errors.hasErrors()) {
            return new SuccessResponseDto("입력값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST.value());
        } return userService.signup(requestDto);
    }

    @PostMapping("/login")
    public SuccessResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
}
