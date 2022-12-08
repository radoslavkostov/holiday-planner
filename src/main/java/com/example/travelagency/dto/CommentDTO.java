package com.example.travelagency.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private String content;

    public CommentDTO() {
    }

    public boolean isEmpty() {
        return (content == null || content.isEmpty());
    }

}
