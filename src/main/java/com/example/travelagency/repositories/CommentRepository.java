package com.example.travelagency.repositories;

import com.example.travelagency.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query(value = "select c from CommentEntity c join ForumEntity f on c.forum.id=f.id where f.id=:forumId order by c.id desc")
    List<CommentEntity> findByForumId(@Param("forumId") Long forumId);
}
