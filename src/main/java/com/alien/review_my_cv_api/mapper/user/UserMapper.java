package com.alien.review_my_cv_api.mapper.user;

import com.alien.review_my_cv_api.dto.authentication.registration.RegistrationRequestDto;
import com.alien.review_my_cv_api.entity.User;

public interface UserMapper {
    User toEntity(RegistrationRequestDto request);
}
