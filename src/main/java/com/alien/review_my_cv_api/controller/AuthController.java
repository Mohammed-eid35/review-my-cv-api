package com.alien.review_my_cv_api.controller;

import com.alien.review_my_cv_api.dto.authentication.login.LoginRequestDto;
import com.alien.review_my_cv_api.dto.authentication.registration.RegistrationRequestDto;
import com.alien.review_my_cv_api.service.authentication.AuthService;
import com.alien.review_my_cv_api.dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@Valid @RequestBody RegistrationRequestDto request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto
                        .builder()
                        .status(HttpStatus.CREATED)
                        .success(true)
                        .data(authService.register(request))
                        .build()
                );
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(authService.login(request))
                        .build()
                );
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseDto> refreshToken(@NotNull HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto
                        .builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .data(authService.refreshToken(request))
                        .build()
                );
    }
}
