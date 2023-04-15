package com.example.holidayplanner.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
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

    @Column(columnDefinition = "TEXT")
    private String review;
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private HotelEntity hotel;

    @Override
    public String toString() {
        return "RatingEntity{" +
                "value=" + value +
                ", review='" + review + '\'' +
                '}';
    }
}
