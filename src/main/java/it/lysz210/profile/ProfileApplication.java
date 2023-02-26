package it.lysz210.profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;
import reactor.tools.agent.ReactorDebugAgent;

@SpringBootApplication
public class ProfileApplication {

	public static void main(String[] args) {
		ReactorDebugAgent.init();
		Hooks.enableAutomaticContextPropagation();
		SpringApplication.run(ProfileApplication.class, args);
	}

}
