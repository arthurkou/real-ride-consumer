package ccld_badge.real_ride_consumer.config;

import java.time.format.DateTimeParseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsumerErrorHandler extends DefaultErrorHandler {

    public ConsumerErrorHandler() {
        super(
                (exception, data) ->
                        log.error(
                                "Exception in Real Ride Consumer is {} for the record {}",
                                exception,
                                data));

        addNotRetryableExceptions(SerializationException.class);
        addNotRetryableExceptions(DateTimeParseException.class);
    }
}
