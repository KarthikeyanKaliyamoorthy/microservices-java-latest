package com.banks.cards.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@ToString @AllArgsConstructor @NoArgsConstructor
public class Cards extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cardId;
    private String cardNumber;
    private String cardType;
    private String mobileNumber;
    private int totalLimit;
    private int availableAmount;
    private int amountUsed;

}
