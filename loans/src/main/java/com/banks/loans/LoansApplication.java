package com.banks.loans;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Loans REST API",
				description = "Loans REST API for Eazy Banks",
				version = "1.0.0",
				license = @License(
						name = "Apache 2.0",
						url = "https://www.apache.org/licenses/LICENSE-2.0"
				) ,
				contact = @Contact(
						name = "Support Team",
						email = "tech_support@eazybanks.com"
				)
		)
)
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}

}
