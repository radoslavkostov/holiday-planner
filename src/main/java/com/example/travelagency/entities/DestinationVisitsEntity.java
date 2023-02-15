package com.example.travelagency.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "destination_visits")
@NoArgsConstructor
@Data
public class DestinationVisitsEntity extends BaseEntity{

    @OneToOne
    private TravelDestinationEntity travelDestinationEntity;

    private Long visits;

}
