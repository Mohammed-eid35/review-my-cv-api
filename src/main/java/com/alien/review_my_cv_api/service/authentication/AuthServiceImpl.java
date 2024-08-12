package com.alien.review_my_cv_api.service.authentication;

import com.alien.review_my_cv_api.config.security.JwtService;
import com.alien.review_my_cv_api.dto.authentication.login.LoginRequestDto;
import com.alien.review_my_cv_api.dto.authentication.login.LoginResponseDto;
import com.alien.review_my_cv_api.dto.authentication.logout.LogoutResponseDto;
import com.alien.review_my_cv_api.dto.authentication.registration.RegistrationRequestDto;
import com.alien.review_my_cv_api.dto.authentication.registration.RegistrationResponseDto;
import com.alien.review_my_cv_api.dto.authentication.token.RefreshTokenResponseDto;
import com.alien.review_my_cv_api.mapper.user.UserMapper;
import com.alien.review_my_cv_api.service.RoleService;
import com.alien.review_my_cv_api.service.email.EmailService;
import com.alien.review_my_cv_api.service.user.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final RoleService roleService;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
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

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userService.getUserByEmail(request.getEmail());
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return LoginResponseDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public RefreshTokenResponseDto refreshToken(@NonNull HttpServletRequest request) {
        var refreshToken = jwtService.extractJwtFromRequest(request);
        var userEmail = jwtService.extractUsername(refreshToken);
        var user = userService.getUserByEmail(userEmail);

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new IllegalArgumentException("Invalid token");
        }

        return RefreshTokenResponseDto
                .builder()
                .accessToken(jwtService.generateAccessToken(user))
                .build();
    }

    @Override
    public LogoutResponseDto logout(@NonNull HttpServletRequest request) {
        // TODO: Implement logout

        return LogoutResponseDto
                .builder()
                .message("Logout successful")
                .build();
    }
}
