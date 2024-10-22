package com.banks.cards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CardsAlreadyExistsException extends RuntimeException {
    public CardsAlreadyExistsException(String message) {
        super(message);
    }
}
