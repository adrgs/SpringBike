package site.springbike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import site.springbike.database.DatabaseManager;
import site.springbike.model.Client;
import site.springbike.model.User;
import site.springbike.repository.ModelRepository;

import java.io.IOException;
import java.sql.Connection;

@SpringBootApplication
public class SpringBikeApplication {

	public static void main(String[] args) {
		try {
			DatabaseManager.init();

			User user = new Client();
			ModelRepository.useModel(user).selectByPrimaryKey(1);

			System.out.println(user.getUsername());
			System.out.println(user.getEmail());
			System.out.println(user.getPassword());

			SpringApplication.run(SpringBikeApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
