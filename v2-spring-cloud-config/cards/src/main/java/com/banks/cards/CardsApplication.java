package com.banks.cards;

import com.banks.cards.dto.CardsContactInfoDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {CardsContactInfoDto.class})
@OpenAPIDefinition(info = @Info(
		title = "Cards RESTAPI",
		version = "1.0",
		description = "Cards REST API for Eazy banks",
		contact = @Contact(
				name = "TechSupport",
				email = "tech_support@eazybanks.com"
		),
		license = @License(
				name = "Apache 2.0",url = "https://www.apache.org/licenses/LICENSE-2.0"
		)

))
public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}
