package de.thb.MACJEE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class MacjeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MacjeeApplication.class, args);
	}

}
