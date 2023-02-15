package com.example.travelagency.services;

import com.example.travelagency.entities.CommentEntity;
import com.example.travelagency.entities.ForumEntity;
import com.example.travelagency.models.service.CommentServiceModel;
import com.example.travelagency.models.view.CommentViewModel;
import com.example.travelagency.repositories.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ForumService forumService;
    private final ModelMapper modelMapper;

    public CommentService(CommentRepository commentRepository, UserService userService, ForumService forumService, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.forumService = forumService;
        this.modelMapper = modelMapper;
    }

    public List<CommentViewModel> getCommentsByForumId(Long id) {
        return commentRepository.findByForumId(id).stream().map(commentEntity -> modelMapper.map(commentEntity, CommentViewModel.class)).collect(Collectors.toList());
    }

    public void addComment(CommentServiceModel commentServiceModel, Long forumId) {

        CommentEntity commentEntity = modelMapper.map(commentServiceModel, CommentEntity.class);
        commentEntity.setUser(userService.getCurrentUser());
        commentEntity.setTimePosted(LocalDateTime.now());
        commentEntity.setForum(modelMapper.map(forumService.getForumById(forumId), ForumEntity.class));
        commentRepository.save(commentEntity);
    }

    public Long deleteComment(Long id) {
        CommentEntity commentEntity = commentRepository.findById(id).orElse(null);
        if(commentEntity==null){
            return -1L;
        }
        Long forumId = commentEntity.getForum().getId();
        commentRepository.delete(commentEntity);

        return forumId;
    }

}
