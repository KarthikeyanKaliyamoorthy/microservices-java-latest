package com.banks.cards.controller;

import com.banks.cards.constants.CardsConstants;
import com.banks.cards.dto.CardsDto;
import com.banks.cards.dto.ResponseDto;
import com.banks.cards.service.ICardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/cards", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@AllArgsConstructor
@Tag(name = "REST APIs for Cards application")
public class CardsController {

    private ICardsService cardsService;

    @Operation(summary = "Create new card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Card created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@RequestParam
                                                      @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be 10 digits")
                                                      String mobileNumber) {
        cardsService.createCards(mobileNumber);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatusCode(CardsConstants.STATUS_201);
        responseDto.setStatusMsg(CardsConstants.MESSAGE_201);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "Fetch card details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Card details fetched successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCardsDetails(@RequestParam
                                                          @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be 10 digits")
                                                          String mobileNumber) {
        CardsDto cardsDto = cardsService.fetchCards(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
    }

    @Operation(summary = "Update card details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Card details updated successfully"),
            @ApiResponse(responseCode = "417", description = "Update operation failed. Please try again or contact Dev team"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCardsDetails(@RequestBody @Valid CardsDto cardsDto) {
        boolean isUpdated = cardsService.updateCard(cardsDto);
        if(isUpdated) {
            ResponseDto responseDto = new ResponseDto();
            responseDto.setStatusCode(CardsConstants.STATUS_200);
            responseDto.setStatusMsg(CardsConstants.MESSAGE_200);
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } else {
            ResponseDto responseDto = new ResponseDto();
            responseDto.setStatusCode(CardsConstants.STATUS_417);
            responseDto.setStatusMsg(CardsConstants.MESSAGE_417_UPDATE);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseDto);
        }

    }

    @Operation(summary = "Delete card details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Card details deleted successfully"),
            @ApiResponse(responseCode = "417", description = "Delete operation failed. Please try again or contact Dev team"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCardsDetails(@RequestParam @Pattern(regexp = "^\\d{10}$",
            message = "Mobile number should be 10 digits") String mobileNumber) {
        boolean isDeleted = cardsService.deleteCard(mobileNumber);
        if(isDeleted) {
            ResponseDto responseDto = new ResponseDto();
            responseDto.setStatusCode(CardsConstants.STATUS_200);
            responseDto.setStatusMsg(CardsConstants.MESSAGE_200);
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } else {
            ResponseDto responseDto = new ResponseDto();
            responseDto.setStatusCode(CardsConstants.STATUS_417);
            responseDto.setStatusMsg(CardsConstants.MESSAGE_417_DELETE);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseDto);
        }
    }


}
