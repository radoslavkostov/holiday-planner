package com.example.travelagency.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ratings")
@Data
@NoArgsConstructor
public class RatingEntity extends BaseEntity {

    private Integer value;
    private String review;
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private HotelEntity hotel;

}
