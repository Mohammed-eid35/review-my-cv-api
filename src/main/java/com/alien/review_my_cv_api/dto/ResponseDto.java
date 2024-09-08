package com.alien.review_my_cv_api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ResponseDto {
    private HttpStatus status;
    private boolean success;
    private Object data;
    private Object error;
}
