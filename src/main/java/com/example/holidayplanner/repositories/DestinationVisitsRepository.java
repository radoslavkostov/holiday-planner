package com.example.holidayplanner.repositories;

import com.example.holidayplanner.entities.DestinationVisitsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DestinationVisitsRepository extends JpaRepository<DestinationVisitsEntity, Long> {
    Optional<DestinationVisitsEntity> findByTravelDestinationEntityId(Long travelDestinationEntity_id);
}
