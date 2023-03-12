package com.safakadir.basketballplayerapi;

import com.safakadir.basketballplayerapi.config.RsaJwtKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({RsaJwtKeyProperties.class})
public class BasketballPlayerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasketballPlayerApiApplication.class, args);
	}

}
