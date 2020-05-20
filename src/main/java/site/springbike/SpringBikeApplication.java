package site.springbike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.ui.Model;
import site.springbike.database.DatabaseManager;
import site.springbike.email.EmailSender;

import javax.validation.constraints.Email;
import java.io.IOException;
import java.sql.Connection;

@SpringBootApplication
public class SpringBikeApplication {

	public static void main(String[] args) {
		try {
			DatabaseManager.init();
			EmailSender.init();

			SpringApplication.run(SpringBikeApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringBikeApplication.class);
	}

}
