package com.example.travelagency.models.view;

import com.example.travelagency.entities.CommentEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ForumViewModel {
    private Long id;
    private String name;
    private List<CommentEntity> comments;
}
