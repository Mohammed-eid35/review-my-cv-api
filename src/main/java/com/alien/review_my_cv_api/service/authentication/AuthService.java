package com.alien.review_my_cv_api.service.authentication;

import com.alien.review_my_cv_api.dto.authentication.logout.LogoutResponseDto;
import com.alien.review_my_cv_api.dto.authentication.login.LoginRequestDto;
import com.alien.review_my_cv_api.dto.authentication.login.LoginResponseDto;
import com.alien.review_my_cv_api.dto.authentication.registration.RegistrationRequestDto;
import com.alien.review_my_cv_api.dto.authentication.registration.RegistrationResponseDto;
import com.alien.review_my_cv_api.dto.authentication.token.RefreshTokenRequestDto;
import com.alien.review_my_cv_api.dto.authentication.token.RefreshTokenResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

public interface AuthService {
    RegistrationResponseDto register(RegistrationRequestDto request);
    LoginResponseDto login(LoginRequestDto request);
    RefreshTokenResponseDto refreshToken(@NotNull HttpServletRequest request);
    LogoutResponseDto logout(@NonNull HttpServletRequest request);
}
