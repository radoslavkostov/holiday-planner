package com.example.holidayplanner.services;

import com.example.holidayplanner.entities.CommentEntity;
import com.example.holidayplanner.entities.ForumEntity;
import com.example.holidayplanner.entities.UserEntity;
import com.example.holidayplanner.models.service.CommentServiceModel;
import com.example.holidayplanner.models.view.CommentViewModel;
import com.example.holidayplanner.models.view.ForumViewModel;
import com.example.holidayplanner.repositories.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ForumService forumService;

    @Mock
    private UserService userService;

    private ModelMapper modelMapper = new ModelMapper();

    private CommentService commentService;

    @BeforeEach
    void setUp(){
        commentService = new CommentService(commentRepository, userService, forumService, modelMapper);
    }

    @Test
    public void getCommentsByForumId_ShouldReturnListOfCommentViewModels() {
        // Arrange
        Long forumId = 1L;
        List<CommentEntity> commentEntities = new ArrayList<>();
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(1L);
        commentEntity.setContent("Test content");
        commentEntities.add(commentEntity);

        when(commentRepository.findByForumId(forumId)).thenReturn(commentEntities);

        // Act
        List<CommentViewModel> result = commentService.getCommentsByForumId(forumId);

        // Assert
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Test content", result.get(0).getContent());
    }

    @Test
    public void addComment_ShouldAddCommentToRepository() {
        // Arrange
        Long forumId = 1L;
        CommentServiceModel commentServiceModel = new CommentServiceModel();
        commentServiceModel.setContent("Test content");

        UserEntity userEntity = new UserEntity();
        when(userService.getCurrentUser()).thenReturn(userEntity);

        ForumEntity forumEntity = new ForumEntity();
        ForumViewModel forumViewModel = modelMapper.map(forumEntity, ForumViewModel.class);
        when(forumService.getForumById(forumId)).thenReturn(forumViewModel);

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(1L);
        commentEntity.setContent("Test content");
        commentEntity.setUser(userEntity);
        commentEntity.setTimePosted(LocalDateTime.now());
        commentEntity.setForum(forumEntity);

        when(commentRepository.save(any(CommentEntity.class))).thenReturn(commentEntity);

        // Act
        commentService.addComment(commentServiceModel, forumId);

        // Assert
        verify(commentRepository).save(any(CommentEntity.class));
    }

    @Test
    public void deleteComment_ShouldDeleteCommentFromRepository() {
        // Arrange
        Long id = 1L;
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(id);
        commentEntity.setForum(new ForumEntity());

        when(commentRepository.findById(id)).thenReturn(Optional.of(commentEntity));

        // Act
        Long result = commentService.deleteComment(id);

        // Assert
        assertEquals(commentEntity.getForum().getId(), result);
        verify(commentRepository).delete(commentEntity);
    }

    @Test
    public void deleteComment_ShouldReturnNegativeOneIfCommentDoesNotExist() {
        // Arrange
        Long id = 1L;
        when(commentRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Long result = commentService.deleteComment(id);

        // Assert
        assertEquals(-1L, result);
        verify(commentRepository, never()).delete(any(CommentEntity.class));
    }

}
