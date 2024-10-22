package com.banks.cards.dto;

import com.banks.cards.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CardsDto  {
    @NotEmpty(message = "Card number cannot be empty")
    @Pattern(regexp = "^\\d{12}$", message = "Card number should be 12 digits")
    private String cardNumber;

    @NotEmpty(message = "Card type cannot be empty")
    private String cardType;
    @NotEmpty(message = "Mobile number cannot be empty")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be 10 digits")
    private String mobileNumber;

    @Positive(message = "Total card limit should be greater than zero")
    private int totalLimit;
    @PositiveOrZero(message = "Available amount should be greater than or equal to zero")
    private int availableAmount;
    @PositiveOrZero(message = "Amount used should be greater than or equal to zero")
    private int amountUsed;
}
