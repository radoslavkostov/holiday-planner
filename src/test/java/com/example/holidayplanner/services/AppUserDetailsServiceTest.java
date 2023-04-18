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

    @BeforeEach
    void setUp() {
        appUserDetailsService = new AppUserDetailsService(userRepository);
    }

    @Test
    public void testLoadUserByUsernameThrowsExceptionWhenUserNotFound() {
        String username = "user@com";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> appUserDetailsService.loadUserByUsername(username));

        String expectedMessage = "User with username " + username + " not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testLoadUserByUsernameReturnsUserDetails() {
        String username = "test@example.com";
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword("password");
        UserRoleEntity role = new UserRoleEntity();
        role.setUserRole(UserRoleEnum.MODERATOR);
        user.setUserRoles(Collections.singletonList(role));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = appUserDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MODERATOR")));
    }
}
