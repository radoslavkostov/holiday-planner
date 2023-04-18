package com.example.holidayplanner.models.binding;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CommentAddBindingModel {

    @Size(min = 2, max = 500, message = "Comment should be between 2 and 500 characters.")
    private String content;

    public boolean isEmpty() {
        return (content == null || content.isEmpty());
    }
}
