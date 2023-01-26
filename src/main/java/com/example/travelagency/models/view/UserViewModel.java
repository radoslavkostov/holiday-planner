package com.example.travelagency.models.view;

import com.example.travelagency.entities.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserViewModel {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private List<CommentEntity> comments;
    private List<ReservationEntity> reservations;
    private List<FavoriteEntity> favorites;
    private List<RatingEntity> ratings;
    private List<UserRoleEntity> userRoles;
}
