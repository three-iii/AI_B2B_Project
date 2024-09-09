package com.three_iii.service.controller;

import com.three_iii.service.application.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;


}
