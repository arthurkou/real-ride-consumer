package ccld_badge.real_ride_consumer.service;

import ccld_badge.real_ride_consumer.entity.RealRideEntity;
import ccld_badge.real_ride_consumer.repository.RealRideEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RealRideEventService {

    private final ObjectMapper objectMapper;
    private final RealRideEventRepository realRideEventRepository;

    public RealRideEventService(ObjectMapper objectMapper, RealRideEventRepository realRideRepository) {
        this.objectMapper = objectMapper;
        this.realRideEventRepository = realRideRepository;
    }

    public void process(String message) throws JsonProcessingException {
        RealRideEntity realRideMessage = objectMapper.readValue(message, RealRideEntity.class);
        realRideEventRepository.save(realRideMessage);
    }
}
