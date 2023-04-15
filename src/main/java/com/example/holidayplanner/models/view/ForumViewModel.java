package com.example.holidayplanner.models.view;

import com.example.holidayplanner.entities.CommentEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ForumViewModel {
    private Long id;
    private String name;
    private String shortDescription;
    private List<CommentEntity> comments;
}
