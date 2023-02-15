package com.example.travelagency.web;

import com.example.travelagency.entities.UserEntity;
import com.example.travelagency.models.binding.UserSearchBindingModel;
import com.example.travelagency.models.view.UserViewModel;
import com.example.travelagency.services.UserService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    void adminPanel_ShouldRedirectToIndexPageIfCurrentUserIsNotAdmin() throws Exception{

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userService.getCurrentUser()).thenReturn(userEntity);
        when(userService.hasRole(anyString(), anyLong())).thenReturn(false);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/admin-panel"))
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void adminPanel_WithInvalidUserSearchBindingModel_ShouldRedirectToAdminPanelView() throws Exception {
        UserSearchBindingModel userSearchBindingModel = new UserSearchBindingModel();
        userSearchBindingModel.setEmail("email");
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userService.getCurrentUser()).thenReturn(userEntity);
        when(userService.hasRole(anyString(), anyLong())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin-panel").flashAttr("userSearchBindingModel", userSearchBindingModel))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-panel"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    void adminPanel_WithValidUserSearchBindingModel_ShouldReturnAdminPanelView() throws Exception {
        UserSearchBindingModel userSearchBindingModel = new UserSearchBindingModel();
        userSearchBindingModel.setEmail("");
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userService.getCurrentUser()).thenReturn(userEntity);
        when(userService.hasRole(anyString(), anyLong())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin-panel").flashAttr("userSearchBindingModel", userSearchBindingModel))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-panel"))
                .andExpect(model().attributeDoesNotExist("users"));
    }

    @Test
    void userDetails_ShouldRedirectToIndexPageIfCurrentUserIsNotAdmin() throws Exception{

        UserEntity userEntity = new UserEntity();
        Long userId = 1L;
        userEntity.setId(userId);
        when(userService.getCurrentUser()).thenReturn(userEntity);
        when(userService.hasRole(anyString(), anyLong())).thenReturn(false);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/user-details/"+userId))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void userDetailsTest() throws Exception {
        UserEntity userEntity = new UserEntity();
        Long userId = 1L;
        userEntity.setId(userId);
        when(userService.getCurrentUser()).thenReturn(userEntity);
        when(userService.hasRole(anyString(), anyLong())).thenReturn(true);
        when(userService.findById(anyLong())).thenReturn(modelMapper.map(userEntity, UserViewModel.class));

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/user-details/"+userId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("isModerator"))
                .andExpect(view().name("user-details"));

    }

    @Test
    void grantMod_ShouldRedirectToIndexPageIfCurrentUserIsNotAdmin() throws Exception{

        UserEntity userEntity = new UserEntity();
        Long userId = 1L;
        userEntity.setId(userId);
        when(userService.getCurrentUser()).thenReturn(userEntity);
        when(userService.hasRole(anyString(), anyLong())).thenReturn(false);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/grant-mod/"+userId))
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void grantModTest() throws Exception {
        UserEntity userEntity = new UserEntity();
        Long userId = 1L;
        userEntity.setId(userId);
        when(userService.getCurrentUser()).thenReturn(userEntity);
        when(userService.hasRole(anyString(), anyLong())).thenReturn(true);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/grant-mod/"+userId))
                .andExpect(view().name("redirect:/user-details/"+userId));

    }

    @Test
    void revokeMod_ShouldRedirectToIndexPageIfCurrentUserIsNotAdmin() throws Exception{

        UserEntity userEntity = new UserEntity();
        Long userId = 1L;
        userEntity.setId(userId);
        when(userService.getCurrentUser()).thenReturn(userEntity);
        when(userService.hasRole(anyString(), anyLong())).thenReturn(false);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/revoke-mod/"+userId))
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void revokeModTest() throws Exception {
        UserEntity userEntity = new UserEntity();
        Long userId = 1L;
        userEntity.setId(userId);
        when(userService.getCurrentUser()).thenReturn(userEntity);
        when(userService.hasRole(anyString(), anyLong())).thenReturn(true);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/revoke-mod/"+userId))
                .andExpect(view().name("redirect:/user-details/"+userId));

    }

}
