package it.lysz210.profile.commands;

import com.pulumi.Pulumi;
import com.pulumi.core.Output;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;


@Service
public class PulumiCmd implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        Pulumi.run(ctx ->
                ctx.export("exampleOutput", Output.of("example"))
        );
    }
}
