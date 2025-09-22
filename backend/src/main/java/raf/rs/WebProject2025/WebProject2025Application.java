package raf.rs.WebProject2025;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class WebProject2025Application {

	public static void main(String[] args) {
		SpringApplication.run(WebProject2025Application.class, args);
	}

}
