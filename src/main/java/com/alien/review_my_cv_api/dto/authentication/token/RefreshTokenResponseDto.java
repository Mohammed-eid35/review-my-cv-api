package com.alien.review_my_cv_api.dto.authentication.token;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RefreshTokenResponseDto {
    private String accessToken;
}
