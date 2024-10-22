package com.banks.loans.service.impl;

import com.banks.loans.constants.LoansConstants;
import com.banks.loans.dto.LoansDto;
import com.banks.loans.entity.Loans;
import com.banks.loans.exception.LoansAlreadyExistsException;
import com.banks.loans.exception.ResourceNotFoundException;
import com.banks.loans.mapper.LoansMapper;
import com.banks.loans.repository.ILoansRepository;
import com.banks.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

    ILoansRepository loansRepository;
    /**
     * Creates a new loan
     *
     * @param mobileNumber
     */
    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> loans = loansRepository.findByMobileNumber(mobileNumber);
        if (loans.isPresent()) {
            throw new LoansAlreadyExistsException("Loan already exists for given mobile number: "+mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new loan details
     */
    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setIntrestRate(10.0);
        newLoan.setDuration(10);
        return newLoan;
    }

    /**
     * Fetches loan details
     *
     * @param mobileNumber
     * @return - LoansDto schema
     */
    @Override
    public LoansDto fetchLoanDetails(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loans", "mobileNumber", mobileNumber)
        );

        return LoansMapper.mapToLoanDto(new LoansDto(), loans);
    }

    /**
     * Updates loan details
     *
     * @param loansDto
     * @return true if loan is updated
     */
    @Override
    public boolean updateLoan(LoansDto loansDto) {

        Loans loans = loansRepository.findByMobileNumber(loansDto.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loans", "mobileNumber", loansDto.getMobileNumber())
        );
        LoansMapper.mapToLoan(loans, loansDto);
        loansRepository.save(loans);
        return true;
    }

    /**
     * Deletes loan details
     *
     * @param mobileNumber
     * @return true if loan is deleted
     */
    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loans", "mobileNumber", mobileNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }
}
