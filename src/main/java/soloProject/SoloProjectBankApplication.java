package soloProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.fdmgroup.repositories")
@EntityScan(basePackages = "com.fdmgroup.entities")
public class SoloProjectBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoloProjectBankApplication.class, args);
	}

}
