package com.scp.dronizone.fleet.repository;

import com.scp.dronizone.fleet.entity.DronePosition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneTelemetryRepository extends MongoRepository<DronePosition, String> {

    @Query("{ 'drone_id' : ?0 }")
    public DronePosition[] findAllTelemetryByDroneId(int droneId);

}

