package com.alien.review_my_cv_api.service.authentication;

import com.alien.review_my_cv_api.dto.authentication.registration.RegistrationRequestDto;
import com.alien.review_my_cv_api.dto.authentication.registration.RegistrationResponseDto;

public interface AuthService {
    RegistrationResponseDto register(RegistrationRequestDto request);
}
