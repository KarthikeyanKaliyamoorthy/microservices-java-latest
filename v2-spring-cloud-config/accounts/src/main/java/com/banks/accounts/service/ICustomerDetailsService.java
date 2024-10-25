package com.banks.accounts.service;

import com.banks.accounts.dto.CustomerDetialsDto;
import com.banks.accounts.dto.CustomerDto;

public interface ICustomerDetailsService {

    /**
     *
     * @param mobileNum
     * @return account details
     */
    CustomerDetialsDto getCustomerDetails(String mobileNum);
}
