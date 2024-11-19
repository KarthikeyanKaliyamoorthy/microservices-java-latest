package com.banks.accounts.service.client;

import com.banks.accounts.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallBackClient implements LoansFeignClient{
    @Override
    public ResponseEntity<LoansDto> fetchLoan(String correlationId, String mobileNumber) {
        return null;
    }
}
