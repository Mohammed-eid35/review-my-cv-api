package com.alien.review_my_cv_api.dto.authentication.registration;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationResponseDto {
    private String message;
}
