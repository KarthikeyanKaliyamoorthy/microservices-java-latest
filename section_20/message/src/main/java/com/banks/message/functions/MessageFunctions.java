package com.banks.message.functions;

import com.banks.message.dto.AccountMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    private static final Logger logger = LoggerFactory.getLogger(MessageFunctions.class);


    /**
     *
     * @return accountMessageDto
     */
    @Bean
    public Function<AccountMessageDto, AccountMessageDto> email() {
        return accountMessageDto -> {
            logger.info("Sending email with details {}", accountMessageDto.toString());
            return accountMessageDto;
        };
    }

    /**
     *
     * @return accountNumber
     */
    @Bean
    public Function<AccountMessageDto, Long> sms() {
        return accountMessageDto -> {
            logger.info("Sending sms with details {}", accountMessageDto.toString());
            return accountMessageDto.accountNumber();
        };
    }
}
