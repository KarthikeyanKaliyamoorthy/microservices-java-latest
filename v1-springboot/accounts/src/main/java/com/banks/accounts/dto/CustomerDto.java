package com.banks.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "Customer", description = "Schema to hold Customer  and Accounts details")
public class CustomerDto {
    @Schema(description = "Customer name", example = "John Doe")
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 characters")
    private String name;

    @Schema(description = "Customer email address", example = "H5JkG@example.com")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(description = "Customer mobile number", example = "1234567890")
    @NotEmpty(message = "Mobile number cannot be empty")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be 10 digits")
    private String mobileNumber;
    private AccountsDto accountsDto;
}
