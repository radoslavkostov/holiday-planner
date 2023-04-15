package com.example.holidayplanner.models.view;

import com.example.holidayplanner.entities.ForumEntity;
import com.example.holidayplanner.entities.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentViewModel {
    private Long id;
    private String content;
    private UserEntity user;
    private ForumEntity forum;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timePosted;
}
