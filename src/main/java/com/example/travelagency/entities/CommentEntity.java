package com.example.travelagency.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "comments")
@NoArgsConstructor
@Data
public class CommentEntity extends BaseEntity {

    private String content;
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private ForumEntity forum;
    private LocalDateTime timePosted;

    @Override
    public String toString() {
        return "CommentEntity{" +
                "content='" + content + '\'' +
                ", timePosted=" + timePosted +
                '}';
    }
}
