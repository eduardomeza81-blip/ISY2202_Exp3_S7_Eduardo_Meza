package com.duoc.backend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MyUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MyUserDetailsService myUserDetailsService;

    @Test
    void loadUserByUsername_whenUserExists_returnsUser() {
        User user = new User();
        user.setId(1);
        user.setUsername("eduardo");
        user.setEmail("eduardo@test.com");
        user.setPassword("1234");

        when(userRepository.findByUsername("eduardo")).thenReturn(user);

        UserDetails result = myUserDetailsService.loadUserByUsername("eduardo");

        assertNotNull(result);
        assertEquals("eduardo", result.getUsername());
        assertEquals("1234", result.getPassword());
    }

    @Test
    void loadUserByUsername_whenUserDoesNotExist_throwsException() {
        when(userRepository.findByUsername("inexistente")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () ->
                myUserDetailsService.loadUserByUsername("inexistente"));
    }

    @Test
    void passwordEncoder_shouldReturnBCryptPasswordEncoder() {
        assertNotNull(myUserDetailsService.passwordEncoder());

        String rawPassword = "1234";
        String encodedPassword = myUserDetailsService.passwordEncoder().encode(rawPassword);

        assertNotEquals(rawPassword, encodedPassword);
        assertTrue(myUserDetailsService.passwordEncoder().matches(rawPassword, encodedPassword));
    }
}