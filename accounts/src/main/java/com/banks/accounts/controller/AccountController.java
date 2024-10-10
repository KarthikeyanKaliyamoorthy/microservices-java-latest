package com.banks.accounts.controller;

import com.banks.accounts.constants.AccountsConstants;
import com.banks.accounts.dto.CustomerDto;
import com.banks.accounts.dto.ErrorResponseDto;
import com.banks.accounts.dto.ResponseDto;
import com.banks.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

@Tag(name = "Accounts Rest API for Eazy Bank", description = "Accounts Rest API documentations for all CRUD operations")
@RestController
@RequestMapping(path = "/api/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@AllArgsConstructor
public class AccountController {


    private IAccountsService accountsService;
    @Operation(summary = "Create new REST account",
            description = "REST API to create a new account inside Eazy bank")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request. Please check your request and try again"),
            @ApiResponse(responseCode = "500", description = "An error occurred. Please try again or contact Dev team"
            , content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {

        accountsService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }
    @Operation(summary = "Fetch account details", description = "REST API to fetch account details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details fetched successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request. Please check your request and try again"),
            @ApiResponse(responseCode = "500", description = "An error occurred. Please try again or contact Dev team"
                    , content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
                                                               @Pattern(regexp = "^\\d{10}$",
                                                                       message = "Account number should be 10 digits")
                                                               String mobileNum) {
        CustomerDto accountDetails = accountsService.getAccountDetails(mobileNum);

        return ResponseEntity.status(HttpStatus.OK).body(accountDetails);
    }

    @Operation(summary = "Update account details", description = "REST API to update account details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details updated successfully"),
            @ApiResponse(responseCode = "417", description = "Bad request. Please check your request and try again"),
            @ApiResponse(responseCode = "500", description = "An error occurred. Please try again or contact Dev team",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountsService.updateAccountDetails(customerDto);
        if(isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }
        else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(summary = "Delete account details", description = "REST API to delete account details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details deleted successfully"),
            @ApiResponse(responseCode = "417", description = "Bad request. Please check your request and try again"),
            @ApiResponse(responseCode = "500", description = "An error occurred. Please try again or contact Dev team"
                    , content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
                                                                @Pattern(regexp = "^\\d{10}$",
                                                                        message = "Account number should be 10 digits")
                                                                String mobileNum) {
        boolean isDeleted = accountsService.deleteAccountDetails(mobileNum);

        if(isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }

}
