package com.example.travelagency.models.binding;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CommentAddBindingModel {

    @Size(min = 2, message = "Comment should be at least 2 symbols.")
    private String content;

    public boolean isEmpty() {
        return (content == null || content.isEmpty());
    }
}
