package com.banks.accounts.service.client;

import com.banks.accounts.dto.CardsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards")
public interface CardsFeignClient {
    @GetMapping("/api/v1/cards/fetch")
    public ResponseEntity<CardsDto> fetchCardsDetails(@RequestParam String mobileNumber);
}
