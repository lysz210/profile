package it.lysz210.profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.tools.agent.ReactorDebugAgent;

@SpringBootApplication
public class ProfileApplication {

	public static void main(String[] args) {
		ReactorDebugAgent.init();
		SpringApplication.run(ProfileApplication.class, args);
	}

}
