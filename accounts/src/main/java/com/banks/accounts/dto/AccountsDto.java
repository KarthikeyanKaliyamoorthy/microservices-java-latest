package com.banks.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(name = "Accounts", description = "Details of bank account")
public class AccountsDto {

    @NotEmpty(message = "Account number cannot be empty")
    @Pattern(regexp = "^\\d{10}$", message = "Account number should be 10 digits")
    @Schema(description = "Account number", example = "1234567890")
    private Long accountNumber;

    @Schema(description = "Account type", example = "Savings")
    @NotEmpty(message = "Account type cannot be empty")
    private String accountType;

    @Schema(description = "Branch address", example = "123 Main Street")
    @NotEmpty(message = "Branch address cannot be empty")
    private String branchAddress;
}
