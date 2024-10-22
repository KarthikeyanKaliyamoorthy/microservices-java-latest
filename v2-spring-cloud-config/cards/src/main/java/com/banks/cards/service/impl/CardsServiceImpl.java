package com.banks.cards.service.impl;

import com.banks.cards.constants.CardsConstants;
import com.banks.cards.dto.CardsDto;
import com.banks.cards.entity.Cards;
import com.banks.cards.exception.CardsAlreadyExistsException;
import com.banks.cards.exception.ResourceNotFoundException;
import com.banks.cards.mapper.CardsMapper;
import com.banks.cards.repository.ICardsRepository;
import com.banks.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    ICardsRepository cardsRepository;
    /**
     * Creates a new card.
     *
     * @param - input mobileNumber
     */
    @Override
    public void createCards(String mobileNumber) {
        Optional<Cards> cardsOptional = cardsRepository.findByMobileNumber(mobileNumber);
        if(cardsOptional.isPresent()) {
            throw new CardsAlreadyExistsException("Card already exists with this mobile number "+mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }

    /**
     * @param mobileNumber
     * @return Cards details
     */
    @Override
    public CardsDto fetchCards(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Cards", "Mobile Number", mobileNumber)
        );
        return CardsMapper.mapToCardsDto(cards, new CardsDto());
    }

    /**
     * @param cardsDto
     * @return true if card is updated
     */
    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Cards", "Mobile Number", cardsDto.getMobileNumber())
        );
        cardsRepository.save(CardsMapper.mapToCards(cardsDto, cards));
        return true;
    }

    /**
     * @param mobileNumber
     * @return true if card is deleted
     */
    @Override
    public boolean deleteCard(String mobileNumber) {

        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Cards", "Mobile Number", mobileNumber)
        );
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }
}
