package pt.old.school.sharks.rocketleague.torneios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

@SpringBootApplication
@EnableMongoRepositories
public class TorneiosApplication {
	
	@Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

	public static void main(String[] args) {
		SpringApplication.run(TorneiosApplication.class, args);
	}

	
}
