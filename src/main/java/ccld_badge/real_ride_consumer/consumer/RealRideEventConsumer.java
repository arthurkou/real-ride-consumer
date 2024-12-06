package ccld_badge.real_ride_consumer.consumer;

import ccld_badge.real_ride_consumer.service.RealRideEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RealRideEventConsumer {

    private final RealRideEventService realRideEventService;

    public RealRideEventConsumer(RealRideEventService realRideEventService) {
        this.realRideEventService = realRideEventService;
    }

    @KafkaListener(
            topics = "#{'${spring.kafka.consumer.topic}'}",
            groupId = "#{'${spring.kafka.consumer.group-id}'}",
            containerFactory = "containerFactory")
    public void onMessage(ConsumerRecord<Integer, String> consumerRecord)
            throws JsonProcessingException {
        log.info("ConsumerRecord: {}", consumerRecord);
        realRideEventService.process(consumerRecord.value());
    }
}
