package com.example.travelagency.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "forums")
@NoArgsConstructor
@Data
public class ForumEntity extends BaseEntity {

    private String name;
    private String shortDescription;
    @OneToMany(mappedBy = "forum", fetch = FetchType.EAGER)
    private List<CommentEntity> comments;

    @Override
    public String toString() {
        return "ForumEntity{" +
                "name='" + name + '\'' +
                '}';
    }
}
