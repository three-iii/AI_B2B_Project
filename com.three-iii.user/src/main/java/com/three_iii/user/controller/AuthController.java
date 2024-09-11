package com.three_iii.user.controller;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.three_iii.user.application.AuthService;
import com.three_iii.user.application.dto.SignInRequest;
import com.three_iii.user.application.dto.SignUpRequest;
import com.three_iii.user.exception.Response;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    @Operation(summary = "회원가입", description = "사용자가 회원가입 합니다.")
    public Response<Long> signUp(@RequestBody SignUpRequest signUpRequest) {
        return Response.success(authService.signUp(signUpRequest));
    }

    @PostMapping("/sign-in")
    public Response<String> signIn(
        @RequestBody SignInRequest signInRequest,
        HttpServletResponse response
    ) {
        response.setHeader(AUTHORIZATION, authService.signIn(signInRequest));
        return Response.success("로그인이 완료되었습니다.");
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok().body("통과!");
    }
}
