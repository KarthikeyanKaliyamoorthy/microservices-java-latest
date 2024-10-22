package com.banks.cards.service;

import com.banks.cards.dto.CardsDto;

public interface ICardsService {

    /**
     * Creates a new card.
     * @param - input mobileNumber
     */
    void createCards(String mobileNumber);

    /**
     *
     * @param - input mobileNumber
     * @return Cards details
     */
    CardsDto fetchCards(String mobileNumber);

    /**
     *
     * @param - input cardsDto
     * @return true if card is updated
     */
    boolean updateCard(CardsDto cardsDto);

    /**
     *
     * @param - input mobileNumber
     * @return true if card is deleted
     */
    boolean deleteCard(String mobileNumber);
}
