package ccld_badge.real_ride_consumer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RealRideEventConsumer {

    @KafkaListener(topics = {"real-ride-events"})
    public void onMessage(ConsumerRecord<Integer, String> consumerRecord) {
        log.info("ConsumerRecord: {}", consumerRecord);
    }
}
