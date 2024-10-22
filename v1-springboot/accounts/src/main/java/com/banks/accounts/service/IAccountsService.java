package com.banks.accounts.service;

import com.banks.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     *Creates a new account
     * @param customerDto
     */
    void createAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNum
     * @return account details
     */
    CustomerDto getAccountDetails(String mobileNum);

    /**
     *
     * @param customerDto - input to update account details
     * @return - true if account details are updated
     */
    boolean updateAccountDetails(CustomerDto customerDto);

    /**
     *
     * @param mobileNum is an input
     * @return true if account is deleted
     */
    boolean deleteAccountDetails(String mobileNum);
}
