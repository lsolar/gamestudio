package gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gamestudio.game.minesweeper.consoleui.ConsoleUI;
import gamestudio.game.minesweeper.core.Field;

@Configuration
@SpringBootApplication
public class SpringClient {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringClient.class, args);

	}

	@Bean
	public CommandLineRunner runner(ConsoleUI ui) {
		return args -> ui.run();

	}

	@Bean
	public ConsoleUI consoleUI(Field field) {
		return new ConsoleUI(field);
	}

	@Bean
	public Field field() {
		return new Field(9, 9, 10);
	}
}
