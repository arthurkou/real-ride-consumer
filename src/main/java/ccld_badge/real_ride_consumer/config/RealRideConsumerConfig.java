package ccld_badge.real_ride_consumer.config;

import static org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@EnableKafka
@Slf4j
public class RealRideConsumerConfig {

    private static final String AUTO_OFFSET_RESET_CONFIG_VALUE = "earliest";
    private static final String DESERIALIZER_CLASS_CONFIG_KEY = IntegerDeserializer.class.getName();
    private static final String DESERIALIZER_CLASS_CONFIG_VALUE =
            StringDeserializer.class.getName();

    private final int maxAttemptsForRetry;

    private final int attemptIntervalInSeconds;

    public RealRideConsumerConfig(
            @Value("${spring.kafka.consumer.retry.max-attempts:5}") int maxAttemptsForRetry,
            @Value("${spring.kafka.consumer.retry.attempt-interval-in-seconds:3}")
                    int attemptIntervalInSeconds) {
        this.maxAttemptsForRetry = maxAttemptsForRetry;
        this.attemptIntervalInSeconds = attemptIntervalInSeconds;
    }

    @Bean
    public FixedBackOff fixedBackOff() {
        return new FixedBackOff(1000L * attemptIntervalInSeconds, maxAttemptsForRetry);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Object, String> containerFactory(
            KafkaConnectionProperties connProperties,
            ConsumerErrorHandler consumerErrorHandler,
            ConsumerInterceptor consumerInterceptor) {
        var factory = new ConcurrentKafkaListenerContainerFactory<Object, String>();
        factory.setConsumerFactory(
                new DefaultKafkaConsumerFactory<>(buildConsumerProperties(connProperties)));
        factory.setCommonErrorHandler(consumerErrorHandler);
        factory.setRecordInterceptor(consumerInterceptor);
        return factory;
    }

    private Map<String, Object> buildConsumerProperties(KafkaConnectionProperties connProperties) {
        var props = connProperties.buildConnectionProperties();
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, DESERIALIZER_CLASS_CONFIG_KEY);
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, DESERIALIZER_CLASS_CONFIG_VALUE);
        props.put(AUTO_OFFSET_RESET_CONFIG, AUTO_OFFSET_RESET_CONFIG_VALUE);
        return props;
    }
}
