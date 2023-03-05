package com.jimin.board.controller;

import com.jimin.board.dto.ResponseDto;
import com.jimin.board.dto.SignInDto;
import com.jimin.board.dto.SignInResponseDto;
import com.jimin.board.dto.SignUpDto;
import com.jimin.board.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signUp")
    public ResponseDto<?> signUp (@RequestBody SignUpDto requestBody) {
        ResponseDto<?> result = authService.sighUp(requestBody);
        return result;
    }
    @PostMapping("/signIn")
    public ResponseDto<SignInResponseDto> signIn(@RequestBody SignInDto request) {
        return authService.signIn(request);
    }
}
