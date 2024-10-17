package com.banks.loans.service;

import com.banks.loans.dto.LoansDto;

public interface ILoansService {

    /**
     * Creates a new loan
     * @param mobileNumber
     */
    void createLoan(String mobileNumber);

    /**
     * Fetches loan details
     * @param mobileNumber
     * @return - LoansDto schema
     */
    LoansDto fetchLoanDetails(String mobileNumber);

    /**
     * Updates loan details
     * @param loansDto
     * @return true if loan is updated
     */
    boolean updateLoan(LoansDto loansDto);

    /**
     * Deletes loan details
     * @param mobileNumber
     * @return true if loan is deleted
     */
    boolean deleteLoan(String mobileNumber);
}
