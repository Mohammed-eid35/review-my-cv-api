package com.alien.review_my_cv_api.service.authentication;

import com.alien.review_my_cv_api.dto.authentication.registration.RegistrationRequestDto;
import com.alien.review_my_cv_api.dto.authentication.registration.RegistrationResponseDto;
import com.alien.review_my_cv_api.mapper.user.UserMapper;
import com.alien.review_my_cv_api.service.RoleService;
import com.alien.review_my_cv_api.service.email.EmailService;
import com.alien.review_my_cv_api.service.user.UserService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final RoleService roleService;
    private final EmailService emailService;

    private final UserMapper userMapper;

    @Override
    public RegistrationResponseDto register(RegistrationRequestDto request) {
        if (userService.existsByEmail(request.getEmail())) {
            throw new EntityExistsException("Email already registered");
        }

        if (!roleService.existsByName("USER")) {
            throw new EntityExistsException("Role USER not initialized");
        }

        userService.saveUser(userMapper.toEntity(request));
        emailService.sendVerificationEmail(request.getEmail());

        return RegistrationResponseDto
                .builder()
                .message("User registered successfully, please verify your email")
                .build();
    }
}
