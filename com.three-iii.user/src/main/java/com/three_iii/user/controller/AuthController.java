package com.three_iii.user.controller;

import com.three_iii.user.application.AuthService;
import com.three_iii.user.application.dto.SignUpRequestDto;
import com.three_iii.user.exception.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    @Operation(summary = "회원가입", description = "사용자가 회원가입 합니다.")
    public Response<Long> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        return Response.success(authService.signUp(signUpRequestDto));
    }
}
