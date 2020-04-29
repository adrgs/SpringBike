package site.springbike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import site.springbike.database.DatabaseManager;

import java.io.IOException;
import java.sql.Connection;

@SpringBootApplication
public class SpringBikeApplication {

	public static void main(String[] args) {
		try {
			DatabaseManager.init();
			SpringApplication.run(SpringBikeApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
