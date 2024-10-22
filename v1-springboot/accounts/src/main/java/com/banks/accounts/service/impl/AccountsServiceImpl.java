package com.banks.accounts.service.impl;

import com.banks.accounts.constants.AccountsConstants;
import com.banks.accounts.dto.AccountsDto;
import com.banks.accounts.dto.CustomerDto;
import com.banks.accounts.entity.Accounts;
import com.banks.accounts.entity.Customer;
import com.banks.accounts.exception.CustomerAlreadyExistsException;
import com.banks.accounts.exception.ResourceNotFoundException;
import com.banks.accounts.mapper.AccountsMapper;
import com.banks.accounts.mapper.CustomerMapper;
import com.banks.accounts.repository.AccountsRepository;
import com.banks.accounts.repository.CustomerRepository;
import com.banks.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private CustomerRepository customerRepository;
    private AccountsRepository accountsRepository;


    /**
     * Creates a new account
     *
     * @param customerDto
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer= CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exists with mobile number"+
                    customer.getMobileNumber());
        }

        customerRepository.save(customer);
        accountsRepository.save(createNewAccount(customer));

    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    /**
     *
     * @param mobileNum
     * @return account details
     */
    @Override
    public CustomerDto getAccountDetails(String mobileNum) {

        Customer customer = customerRepository.findByMobileNumber(mobileNum).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobile number", mobileNum)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customer id", String.valueOf(customer.getCustomerId()))
        );

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(accounts, new AccountsDto());
        customerDto.setAccountsDto(accountsDto);
        return customerDto;
    }

    /**
     * @param customerDto - input to update account details
     * @return - true if account details are updated
     */
    @Override
    public boolean updateAccountDetails(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();

        if (accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber().toString()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "Account number", String.valueOf(accountsDto.getAccountNumber()))
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerId", String.valueOf(customerId))
            );

            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated=true;
        }

        return isUpdated;
    }

    @Override
    public boolean deleteAccountDetails(String mobileNum) {

        Customer customer = customerRepository.findByMobileNumber(mobileNum).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobile number", mobileNum)
        );
        customerRepository.deleteById(customer.getCustomerId());
        accountsRepository.deleteByCustomerId(customer.getCustomerId());

        return true;
    }
}
