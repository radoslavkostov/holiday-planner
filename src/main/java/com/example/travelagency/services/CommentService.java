package com.example.travelagency.services;

import com.example.travelagency.dto.CommentDTO;
import com.example.travelagency.entities.CommentEntity;
import com.example.travelagency.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ForumService forumService;

    public CommentService(CommentRepository commentRepository, UserService userService, ForumService forumService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.forumService = forumService;
    }

    public List<CommentEntity> getCommentsByForumId(Long id) {
        return commentRepository.findByForumId(id);
    }

    public void addComment(CommentDTO commentDTO) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(commentDTO.getContent());
        commentEntity.setUser(userService.getCurrentUser());
        commentEntity.setTimePosted(LocalDateTime.now());
        commentEntity.setForum(forumService.getForumById(1L));
        commentRepository.save(commentEntity);
    }
}
