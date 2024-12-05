package ccld_badge.real_ride_consumer.config;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.RecordInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsumerInterceptor implements RecordInterceptor<Object, String> {

    @Override
    public ConsumerRecord<Object, String> intercept(
            ConsumerRecord<Object, String> record, Consumer<Object, String> consumer) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("topic-name", record.topic());
        metadata.put("topic-partition", String.valueOf(record.partition()));
        metadata.put("topic-offset", String.valueOf(record.offset()));
        // TODO log meta data
        log.info("Started processing message");
        return record;
    }

    @Override
    public void success(ConsumerRecord<Object, String> record, Consumer<Object, String> consumer) {
        log.info("Message consumed with success.");
        RecordInterceptor.super.success(record, consumer);
    }

    @Override
    public void failure(
            ConsumerRecord<Object, String> record,
            Exception exception,
            Consumer<Object, String> consumer) {
        log.error(
                "Unable to consume the message, let's try again. Reason: " + exception.getMessage(),
                exception);
        RecordInterceptor.super.failure(record, exception, consumer);
    }
}
