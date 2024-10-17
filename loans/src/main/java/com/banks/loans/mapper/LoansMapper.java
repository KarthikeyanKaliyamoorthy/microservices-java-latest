package com.banks.loans.mapper;

import com.banks.loans.dto.LoansDto;
import com.banks.loans.entity.Loans;

public class LoansMapper {

    public static LoansDto mapToLoanDto( LoansDto loansDto, Loans loans) {
        loansDto.setLoanNumber(loans.getLoanNumber());
        loansDto.setLoanType(loans.getLoanType());
        loansDto.setTotalLoan(loans.getTotalLoan());
        loansDto.setDuration(loans.getDuration());
        loansDto.setIntrestRate(loans.getIntrestRate());
        loansDto.setAmountPaid(loans.getAmountPaid());
        loansDto.setMobileNumber(loans.getMobileNumber());
        loansDto.setOutstandingAmount(loans.getOutstandingAmount());
        return loansDto;
    }

    public static Loans mapToLoan(Loans loans, LoansDto loansDto) {
        loans.setLoanNumber(loansDto.getLoanNumber());
        loans.setLoanType(loansDto.getLoanType());
        loans.setTotalLoan(loansDto.getTotalLoan());
        loans.setDuration(loansDto.getDuration());
        loans.setIntrestRate(loansDto.getIntrestRate());
        loans.setAmountPaid(loansDto.getAmountPaid());
        loans.setMobileNumber(loansDto.getMobileNumber());
        loans.setOutstandingAmount(loansDto.getOutstandingAmount());
        return loans;
    }
}
