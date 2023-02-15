package com.sparta.board.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class SignupRequestDto {

    @NotEmpty (message = "빈칸일 수 없습니다")
    @NotBlank (message = "공백은 입력할 수 없습니다")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z]).{4,10}", message = "아이디는 영문 소문자 및 숫자를 포함해서 만들어야 합니다.")
    private String username;

    @NotEmpty (message = "빈칸일 수 없습니다")
    @NotBlank (message = "공백은 입력할 수 없습니다")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,15}", message = "비밀번호는 영문 대소문자 및 숫자를 포함해서 만들어야 합니다.")
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}
