package com.banks.accounts.controller;

import com.banks.accounts.constants.AccountsConstants;
import com.banks.accounts.dto.*;
import com.banks.accounts.service.IAccountsService;
import com.banks.accounts.service.ICustomerDetailsService;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
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

import java.util.concurrent.TimeoutException;

@Tag(name = "Accounts Rest API for Eazy Bank", description = "Accounts Rest API documentations for all CRUD operations")
@RestController
@RequestMapping(path = "/api/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);


    private final IAccountsService accountsService;
    private final ICustomerDetailsService customerDetailsService;

    public AccountController(IAccountsService accountsService, ICustomerDetailsService customerDetailsService) {
        this.accountsService = accountsService;
        this.customerDetailsService = customerDetailsService;
    }

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;


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

    @Operation(summary = "Fetch Customer details", description = "REST API to fetch account, cards, and loans details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account, cards, and loans details fetched successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request. Please check your request and try again"),
            @ApiResponse(responseCode = "500", description = "An error occurred. Please try again or contact Dev team"
                    , content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/fetch/customer-details")
    public ResponseEntity<CustomerDetialsDto> fetchCustomerDetails( @RequestHeader("eazy-bank-correlation-id") String correlationId,
                                                            @RequestParam
                                                            @Pattern(regexp = "^\\d{10}$",
                                                                   message = "Mobile number should be 10 digits")
                                                           String mobileNum) {
        logger.debug("CorrelationId found in request header: {}", correlationId);
        CustomerDetialsDto customerDetialsDto = customerDetailsService.getCustomerDetails(correlationId, mobileNum);
        return ResponseEntity.status(HttpStatus.OK).body(customerDetialsDto);
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
    @Operation(summary = "Fetch build version details", description = "REST API to fetch build version details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "build version details fetched successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request. Please check your request and try again"),
            @ApiResponse(responseCode = "500", description = "An error occurred. Please try again or contact Dev team"
                    , content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @Retry(name = "getBuildVersion", fallbackMethod = "getBuildVersionFallback")
    @GetMapping("/version")
    public ResponseEntity<String> getBuildVersion() throws TimeoutException {
        logger.debug("getBuildVersion() method is invoked");
        throw new TimeoutException();
        /*return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("Build version: %s",buildVersion));*/
    }

    public ResponseEntity<String> getBuildVersionFallback(Throwable throwable) {
        logger.debug("getBuildVersionFallback() method is invoked");
        return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("Build version: %s",0.1));
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

    @Operation(summary = "Get Contact Info", description = "REST API to fetch Contact Info for accounts micro service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "build version details fetched successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request. Please check your request and try again"),
            @ApiResponse(responseCode = "500", description = "An error occurred. Please try again or contact Dev team"
                    , content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountsContactInfoDto);
    }

}
