package com.banks.cards.controller;

import com.banks.cards.constants.CardsConstants;
import com.banks.cards.dto.CardsContactInfoDto;
import com.banks.cards.dto.CardsDto;
import com.banks.cards.dto.ErrorResponseDto;
import com.banks.cards.dto.ResponseDto;
import com.banks.cards.service.ICardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/cards", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(name = "REST APIs for Cards application")
public class CardsController {

    private static final Logger logger = LoggerFactory.getLogger(CardsController.class);

    private final ICardsService cardsService;

    public CardsController(ICardsService cardsService) {
        this.cardsService = cardsService;
    }

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private CardsContactInfoDto cardsContactInfoDto;

    @Operation(summary = "Create new card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Card created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
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
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCardsDetails(@RequestHeader("eazy-bank-correlation-id") String correlationId,
                                                            @RequestParam
                                                            @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be 10 digits")
                                                          String mobileNumber) {
        logger.debug("CorrelationId found in request header: {}", correlationId);
        CardsDto cardsDto = cardsService.fetchCards(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
    }

    @Operation(summary = "Update card details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Card details updated successfully"),
            @ApiResponse(responseCode = "417", description = "Update operation failed. Please try again or contact Dev team"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
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
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
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

    @Operation(summary = "Fetch build version details", description = "REST API to fetch build version details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "build version details fetched successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request. Please check your request and try again"),
            @ApiResponse(responseCode = "500", description = "An error occurred. Please try again or contact Dev team"
                    , content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/version")
    public ResponseEntity<String> getBuildVersion() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("Build version: %s",buildVersion));
    }

    @Operation(summary = "Fetch Java version details", description = "REST API to fetch Java version details of cards micro service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "build version details fetched successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request. Please check your request and try again"),
            @ApiResponse(responseCode = "500", description = "An error occurred. Please try again or contact Dev team"
                    , content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("Java Version Details: "+environment.getProperty("java.home")+" "+environment.getProperty("java.version"));
    }

    @Operation(summary = "Get Contact Info", description = "REST API to fetch Contact Info for Cards micro service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact info fetched successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request. Please check your request and try again"),
            @ApiResponse(responseCode = "500", description = "An error occurred. Please try again or contact Dev team"
                    , content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/contact-info")
    public ResponseEntity<CardsContactInfoDto> getContactInfo() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(cardsContactInfoDto);
    }

}
