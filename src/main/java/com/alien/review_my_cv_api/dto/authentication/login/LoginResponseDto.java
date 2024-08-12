package com.alien.review_my_cv_api.dto.authentication.login;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;
}
