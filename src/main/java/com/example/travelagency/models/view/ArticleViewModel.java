package com.example.travelagency.models.view;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ArticleViewModel {

    private Long id;
    private String name;
    private String shortDescription;
    private String content;
    private String imageURL;

}
