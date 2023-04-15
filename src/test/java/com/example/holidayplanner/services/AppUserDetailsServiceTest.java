package com.example.holidayplanner.services;

import com.example.holidayplanner.entities.UserEntity;
import com.example.holidayplanner.entities.UserRoleEntity;
import com.example.holidayplanner.enums.UserRoleEnum;
import com.example.holidayplanner.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    private AppUserDetailsService appUserDetailsService;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setUp() {
        appUserDetailsService = new AppUserDetailsService(userRepository);
    }

    @Test
    public void testLoadUserByUsernameThrowsExceptionWhenUserNotFound() {
        String email = "user@com";
        // Arrange
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            appUserDetailsService.loadUserByUsername(email);
        });

        String expectedMessage = "User with email " + email + " not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testLoadUserByUsernameReturnsUserDetails() {
        // Arrange
        String email = "test@example.com";
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword("password");
        UserRoleEntity role = new UserRoleEntity();
        role.setUserRole(UserRoleEnum.MODERATOR);
        user.setUserRoles(Collections.singletonList(role));
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = appUserDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MODERATOR")));
    }
}
