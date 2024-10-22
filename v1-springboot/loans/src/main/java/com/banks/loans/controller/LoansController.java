package com.banks.loans.controller;

import com.banks.loans.constants.LoansConstants;
import com.banks.loans.dto.ErrorResponseDto;
import com.banks.loans.dto.LoansContactInfoDto;
import com.banks.loans.dto.LoansDto;
import com.banks.loans.dto.ResponseDto;
import com.banks.loans.service.ILoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Loans REST API", description = "API for creating and fetching loans")
@RestController
@RequestMapping(path = "/api/v1/loans", produces = "application/json")
@Validated
public class LoansController {

    private final ILoansService loansService;

    public LoansController(ILoansService loansService) {
        this.loansService = loansService;
    }

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private LoansContactInfoDto loansContactInfoDto;

    @Operation(summary = "Create new loan", description = "Creates a new loan for given mobile number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Loan created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request. Please check your request and try again"),
            @ApiResponse(responseCode = "500", description = "Internal server error. Please try again or contact Dev team"
            , content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping(path = "/create")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be 10 digit") String mobileNumber) {
        loansService.createLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
    }

    @Operation(summary = "Fetch loan details", description = "Fetches loan details for given mobile number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request processed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request. Please check your request and try again"),
            @ApiResponse(responseCode = "500", description = "Internal server error. Please try again or contact Dev team"
                    , content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/fetch")
    public ResponseEntity<LoansDto> fetchLoan(@RequestParam @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be 10 digit") String mobileNumber) {
        LoansDto loansDto = loansService.fetchLoanDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loansDto);
    }

    @Operation(summary = "Update loan details", description = "Updates loan details for given mobile number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request processed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request. Please check your request and try again"),
            @ApiResponse(responseCode = "417", description = "Update operation failed. Please try again or contact Dev team"),
            @ApiResponse(responseCode = "500", description = "Internal server error. Please try again or contact Dev team"
                    , content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateLoan(@RequestBody @Valid LoansDto loansDto) {
        boolean isLoanUpdated = loansService.updateLoan(loansDto);
        if(isLoanUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE));
        }

    }

    @Operation(summary = "Delete loan details", description = "Deletes loan details for given mobile number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request processed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request. Please check your request and try again"),
            @ApiResponse(responseCode = "417", description = "Delete operation failed. Please try again or contact Dev team"),
            @ApiResponse(responseCode = "500", description = "Internal server error. Please try again or contact Dev team"
                    , content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoan(@RequestParam @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be 10 digit") String mobileNumber) {
        boolean isLoanDeleted = loansService.deleteLoan(mobileNumber);
        if(isLoanDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE));
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

    @Operation(summary = "Fetch Java version details", description = "REST API to fetch Java version details of accounts micro service")
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

    @Operation(summary = "Get Contact Info", description = "REST API to fetch Contact Info for loans micro service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "build version details fetched successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request. Please check your request and try again"),
            @ApiResponse(responseCode = "500", description = "An error occurred. Please try again or contact Dev team"
                    , content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/contact-info")
    public ResponseEntity<LoansContactInfoDto> getContactInfo() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(loansContactInfoDto);
    }

}
