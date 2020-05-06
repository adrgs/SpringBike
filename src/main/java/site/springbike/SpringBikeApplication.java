package site.springbike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;
import site.springbike.database.DatabaseManager;

import java.io.IOException;
import java.sql.Connection;

@SpringBootApplication
public class SpringBikeApplication {

	public static void main(String[] args) {
		try {
			DatabaseManager.init();

//			User user = new Client();
//			user = (Client) ModelRepository.useModel(user).selectByPrimaryKey(1);
//
//			System.out.println(user.getUsername());
//			System.out.println(user.getEmail());
//			System.out.println(user.getPassword());
//
//			user.setPassword("cisco12345");
//
//			ModelRepository.useModel(user).updateModel();
//
//			user.setUsername("roby");
//			user.setEmail("originalname@live.com");
//			user.setBalance(null);
//			user.setDateCreated(null);
//
//
//			ModelRepository.useModel(user).insertModel();

			SpringApplication.run(SpringBikeApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
