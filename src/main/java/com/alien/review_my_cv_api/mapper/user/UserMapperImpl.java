package com.alien.review_my_cv_api.mapper.user;

import com.alien.review_my_cv_api.dto.authentication.registration.RegistrationRequestDto;
import com.alien.review_my_cv_api.entity.User;
import com.alien.review_my_cv_api.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public User toEntity(RegistrationRequestDto request) {
        return User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .accountEnabled(true) // TODO: set to false when email verification is implemented
                .roles(List.of(roleService.getRoleByName("USER")))
                .build();
    }
}
