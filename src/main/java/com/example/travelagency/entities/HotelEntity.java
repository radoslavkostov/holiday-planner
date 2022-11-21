package com.example.travelagency.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "hotels")
@NoArgsConstructor
@Data
public class HotelEntity extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "hotel")
    private List<HotelRoomEntity> rooms;

}
