package com.banks.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(name = "Loans", description = "Schema to hold Loans details")
public class LoansDto {
    @Schema(description = "Mobile number of the customer", example = "1234567890")
    @NotEmpty(message = "Mobile number cannot be empty")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be 10 digit")
    private String mobileNumber;

    @Schema(description = "Loan type", example = "Home Loan")
    @NotEmpty(message = "Loan type cannot be empty")
    private String loanType;

    @Schema(description = "Loan number", example = "1234567890")
    @NotEmpty(message = "Loan number cannot be empty")
    private String loanNumber;

    @Schema(description = "Total loan", example = "100000")
    @Positive(message = "Total loan should be positive number")
    private int totalLoan;

    @Schema(description = "Amount paid", example = "50000")
    @PositiveOrZero(message = "Amount paid should be positive or zero")
    private int amountPaid;

    @Schema(description = "Outstanding amount", example = "50000")
    @PositiveOrZero(message = "Outstanding amount should be positive or zero")
    private int outstandingAmount;

    @Schema(description = "Interest rate", example = "10.5")
    @Positive(message = "Interest rate should be positive number")
    private double intrestRate;

    @Schema(description = "Duration", example = "5")
    @Min(value = 1, message = "Duration should be between 1 and 25 years")
    @Max(value = 25, message = "Duration should be between 1 and 25 years")
    private int duration;
}
