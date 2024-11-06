package com.banks.accounts.service.client;

import com.banks.accounts.dto.CardsDto;
import com.banks.accounts.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loans")
public interface LoansFeignClient {
    @GetMapping("/api/v1/loans/fetch")
    public ResponseEntity<LoansDto> fetchLoan(@RequestHeader("eazy-bank-correlation-id") String correlationId, @RequestParam String mobileNumber);
}
