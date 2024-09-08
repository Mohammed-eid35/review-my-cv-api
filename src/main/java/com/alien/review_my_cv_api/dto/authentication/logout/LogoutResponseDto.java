package com.alien.review_my_cv_api.dto.authentication.logout;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LogoutResponseDto {
    private String message;
}
