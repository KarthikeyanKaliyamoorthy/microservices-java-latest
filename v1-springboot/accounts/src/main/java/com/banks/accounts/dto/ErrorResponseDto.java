package com.banks.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Error Response", name = "ErrorResponse")
public class ErrorResponseDto {

    @Schema(description = "API path", example = "/api/v1/accounts/create")
    private String apiPath;
    @Schema(description = "Error code", example = "400")
    private HttpStatus errorCode;
    @Schema(description = "Error message", example = "Bad request. Please check your request and try again")
    private String errorMessage;
    @Schema(description = "Error time", example = "2023-08-11T14:00:00.000+00:00")
    private LocalDateTime errorTime;
}
