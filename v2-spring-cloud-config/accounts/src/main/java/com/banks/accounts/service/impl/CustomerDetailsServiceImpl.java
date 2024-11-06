package com.banks.accounts.service.impl;

import com.banks.accounts.dto.AccountsDto;
import com.banks.accounts.dto.CardsDto;
import com.banks.accounts.dto.CustomerDetialsDto;
import com.banks.accounts.dto.LoansDto;
import com.banks.accounts.entity.Accounts;
import com.banks.accounts.entity.Customer;
import com.banks.accounts.exception.ResourceNotFoundException;
import com.banks.accounts.mapper.AccountsMapper;
import com.banks.accounts.mapper.CustomerDetailsMapper;
import com.banks.accounts.repository.AccountsRepository;
import com.banks.accounts.repository.CustomerRepository;
import com.banks.accounts.service.ICustomerDetailsService;
import com.banks.accounts.service.client.CardsFeignClient;
import com.banks.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerDetailsServiceImpl implements ICustomerDetailsService {

    private CustomerRepository customerRepository;
    private AccountsRepository accountsRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    /**
     * @param mobileNum
     * @return account details
     */
    @Override
    public CustomerDetialsDto getCustomerDetails(String correlationId, String mobileNum) {

        Customer customer = customerRepository.findByMobileNumber(mobileNum).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobile number", mobileNum)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customer id", String.valueOf(customer.getCustomerId()))
        );

        CustomerDetialsDto customerDetialsDto = CustomerDetailsMapper.mapToCustomerDetailsDto(customer, new CustomerDetialsDto());
        AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(accounts, new AccountsDto());
        customerDetialsDto.setAccountsDto(accountsDto);
        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardsDetails(correlationId, mobileNum);
        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoan(correlationId, mobileNum);

        customerDetialsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        customerDetialsDto.setLoansDto(loansDtoResponseEntity.getBody());
        return customerDetialsDto;
    }
}
