package it.lysz210.profile.configs;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomObervationHandler implements ObservationHandler<Observation.Context> {
    private static final Logger log = LoggerFactory.getLogger(CustomObervationHandler.class);

    @Override
    public void onStart(Observation.Context context) {
        log.info("Before running the observation for context [{}]", context.getName());
    }

    @Override
    public void onStop(Observation.Context context) {
        log.info("After running the observation for context [{}]", context.getName());
    }

    @Override
    public boolean supportsContext(Observation.Context context) {
        return true;
    }
}
