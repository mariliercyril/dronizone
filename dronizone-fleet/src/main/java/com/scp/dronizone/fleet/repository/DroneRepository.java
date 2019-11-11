package com.scp.dronizone.fleet.repository;

import com.scp.dronizone.fleet.entity.Drone;
import com.scp.dronizone.fleet.states.DroneState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DroneRepository extends MongoRepository<Drone, String> {

    @Query("{ 'drone_id' : ?0 }")
    // @Query("{ 'id' : ?0 }")
    public Optional<Drone> findDroneById(int droneId);

    @Query(value="{'drone_id' : ?0}", delete = true)
    public void deleteByDroneId(int droneId);

    @Query("{ 'status': ?0 }")
    public Iterable<Drone> findAllDronesByDroneState(DroneState status);

}