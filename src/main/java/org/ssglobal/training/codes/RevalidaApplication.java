package org.ssglobal.training.codes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.ssglobal.training.codes.repositories.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class RevalidaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RevalidaApplication.class, args);
	}

}
