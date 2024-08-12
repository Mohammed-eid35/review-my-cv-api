package com.alien.review_my_cv_api.dto.authentication.login;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginRequestDto {
    @Email(message = "Email is not valid")
    @NotEmpty(message = "Email is required")
    @NotNull(message = "Email is required")
    private String email;

    @NotEmpty(message = "Password is required")
    @NotNull(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
}
