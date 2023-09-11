package net.binarypaper.example.simplebankaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@PropertySource("classpath:META-INF/build-info.properties")
@OpenAPIDefinition(
	info = @Info(
		title = "${build.name}", 
		description = "${build.description}", 
		version = "${build.version}",
		contact = @Contact(
			name = "${build.developer.name}", 
			email = "${build.developer.email}"
		), 
		license = @License(
			name = "${build.license.name}", 
			url = "${build.license.url}"
		)
	)
)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}