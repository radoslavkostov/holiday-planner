package com.example.travelagency.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
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
    @Column(columnDefinition = "TEXT")
    private String content;
    private String imageURL;

    @Override
    public String toString() {
        return "ArticleEntity{" +
                "name='" + name + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
