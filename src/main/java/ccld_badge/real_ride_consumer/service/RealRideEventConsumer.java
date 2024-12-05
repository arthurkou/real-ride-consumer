package ccld_badge.real_ride_consumer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RealRideEventConsumer {

    @KafkaListener(
            topics = "#{'${spring.kafka.consumer.topic}'}",
            groupId = "#{'${spring.kafka.consumer.group-id}'}",
            containerFactory = "containerFactory")
    public void onMessage(ConsumerRecord<Integer, Integer> consumerRecord) {
        log.info("ConsumerRecord: {}", consumerRecord);
    }
}
