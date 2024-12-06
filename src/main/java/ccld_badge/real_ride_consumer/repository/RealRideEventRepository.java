package ccld_badge.real_ride_consumer.repository;

import ccld_badge.real_ride_consumer.entity.RealRideEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RealRideEventRepository extends JpaRepository<RealRideEntity, Integer> {}
