package com.example.travelagency.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "articles")
@NoArgsConstructor
@Data
public class ArticleEntity extends BaseEntity {

    private String name;
    private String shortDescription;
    private String content;

}
