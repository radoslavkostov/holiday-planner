package com.example.holidayplanner.web;

import com.example.holidayplanner.entities.UserEntity;
import com.example.holidayplanner.models.view.ForumViewModel;
import com.example.holidayplanner.services.CommentService;
import com.example.holidayplanner.services.ForumService;
import com.example.holidayplanner.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class ForumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ForumService forumService;

    @MockBean
    private CommentService commentService;

    @MockBean
    private UserService userService;


    @Test
    void forumsTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/forums"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("forums"))
                .andExpect(view().name("forums"));
    }

    @Test
    void forumTest() throws Exception {
        Long forumId = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userService.getCurrentUser()).thenReturn(userEntity);
        when(forumService.getForumById(anyLong())).thenReturn(new ForumViewModel());
        when(userService.hasRole(anyString(), anyLong())).thenReturn(true);
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/forums/"+forumId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("forum"))
                .andExpect(model().attributeExists("comments"))
                .andExpect(model().attributeExists("isModerator"))
                .andExpect(view().name("forum-comments"));
    }

    @Test
    void deleteCommentTest_ShouldRedirectToIndexPageIfUserIsNotModerator() throws Exception {
        UserEntity userEntity = new UserEntity();
        Long userId = 1L;
        userEntity.setId(userId);
        Long commentId = 1L;
        when(userService.getCurrentUser()).thenReturn(userEntity);
        when(userService.hasRole(anyString(), anyLong())).thenReturn(false);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/delete-comment/"+commentId).with(csrf()))
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void deleteCommentTest_ShouldRedirectToForumPageIfUserIsModerator() throws Exception {
        UserEntity userEntity = new UserEntity();
        Long userId = 1L;
        userEntity.setId(userId);
        Long commentId = 1L;
        Long forumId = 1L;
        when(userService.getCurrentUser()).thenReturn(userEntity);
        when(userService.hasRole(anyString(), anyLong())).thenReturn(true);
        when(commentService.deleteComment(commentId)).thenReturn(forumId);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/delete-comment/"+commentId).with(csrf()))
                .andExpect(view().name("redirect:/forums/"+forumId));
    }



}
