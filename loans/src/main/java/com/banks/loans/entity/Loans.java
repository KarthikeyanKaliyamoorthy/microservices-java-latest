package com.banks.loans.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter @Getter @ToString @AllArgsConstructor @NoArgsConstructor
public class Loans extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int loanId;
    private String mobileNumber;
    private String loanType;
    private String loanNumber;
    private int totalLoan;
    private int amountPaid;
    private int outstandingAmount;
    private double intrestRate;
    private int duration;
}
