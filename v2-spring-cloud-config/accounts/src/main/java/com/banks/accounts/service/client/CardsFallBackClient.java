package com.banks.accounts.service.client;

import com.banks.accounts.dto.CardsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallBackClient implements CardsFeignClient{
    @Override
    public ResponseEntity<CardsDto> fetchCardsDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
