package com.example.holidayplanner.services;

import com.example.holidayplanner.entities.UserEntity;
import com.example.holidayplanner.entities.UserRoleEntity;
import com.example.holidayplanner.enums.UserRoleEnum;
import com.example.holidayplanner.models.service.UserServiceModel;
import com.example.holidayplanner.models.view.UserViewModel;
import com.example.holidayplanner.repositories.UserRepository;
import com.example.holidayplanner.repositories.UserRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    private PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private UserRoleService userRoleService;

    private ModelMapper modelMapper = new ModelMapper();

    private UserService userService;

    String adminPass = "topsecret";


    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, userRoleRepository, passwordEncoder, userDetailsService, userRoleService, modelMapper, adminPass);
    }

    @Test
    void testHasRoleReturnsTrue() {
        UserEntity user = new UserEntity();
        UserRoleEntity role = new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN);
        user.setUserRoles(Collections.singletonList(role));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        boolean result = userService.hasRole("ADMIN", 1L);

        assertTrue(result);
    }

    @Test
    void testHasRoleReturnsFalseIfUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = userService.hasRole("ADMIN", 1L);

        assertFalse(result);
    }

    @Test
    void testHasRoleReturnsFalseIfUserHasNoRole() {
        UserEntity user = new UserEntity();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        boolean result = userService.hasRole("ADMIN", 1L);

        assertFalse(result);
    }

    @Test
    void testFindByEmailReturnsUserViewModelList() {
        List<UserEntity> userList = Arrays.asList(new UserEntity(), new UserEntity());
        when(userRepository.findAllByEmail("test@example.com")).thenReturn(userList);

        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setEmail("test@example.com");
        List<UserViewModel> result = userService.findByEmail(userServiceModel);

        assertEquals(2, result.size());
    }

    @Test
    void testFindByIdReturnsUserViewModel() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserViewModel result = userService.findById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testRevokeModDoesNothingIfUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        userService.revokeMod(1L);

        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void testGrantModAddsModeratorRole() {
        UserEntity user = new UserEntity();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRoleService.findByUserRole(UserRoleEnum.MODERATOR)).thenReturn(new UserRoleEntity());

        userService.grantMod(1L);

        assertEquals(1, user.getUserRoles().size());
        verify(userRepository).save(user);
    }

    @Test
    void testGrantModDoesNothingIfUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        userService.grantMod(1L);

        verify(userRepository, never()).save(any(UserEntity.class));
    }


}
