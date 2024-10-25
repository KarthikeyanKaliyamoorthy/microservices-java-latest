package com.banks.accounts.mapper;

import com.banks.accounts.dto.CustomerDetialsDto;
import com.banks.accounts.entity.Customer;

public class CustomerDetailsMapper {

    public static CustomerDetialsDto mapToCustomerDetailsDto(Customer customer, CustomerDetialsDto customerDto) {
        customerDto.setEmail(customer.getEmail());
        customerDto.setName(customer.getName());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }


}
