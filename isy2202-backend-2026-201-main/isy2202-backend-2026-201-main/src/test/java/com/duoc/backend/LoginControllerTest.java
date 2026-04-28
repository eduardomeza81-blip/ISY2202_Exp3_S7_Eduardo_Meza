package com.duoc.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @Mock
    private JWTAuthenticationConfig jwtAuthenticationConfig;

    @Mock
    private MyUserDetailsService userDetailsService;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private LoginController loginController;

    private LoginController.LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginController.LoginRequest();
        loginRequest.setUsername("eduardo");
        loginRequest.setPassword("1234");
    }

    @Test
    void login_whenCredentialsAreValid_returnsToken() {
        when(userDetailsService.loadUserByUsername("eduardo")).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("eduardo");
        when(userDetails.getPassword()).thenReturn("1234");
        when(jwtAuthenticationConfig.getJWTToken("eduardo")).thenReturn("fake-jwt-token");

        String result = loginController.login(loginRequest);

        assertNotNull(result);
        assertEquals("fake-jwt-token", result);
        verify(userDetailsService, times(1)).loadUserByUsername("eduardo");
        verify(jwtAuthenticationConfig, times(1)).getJWTToken("eduardo");
    }

    @Test
    void login_whenPasswordIsInvalid_throwsException() {
        when(userDetailsService.loadUserByUsername("eduardo")).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("eduardo");
        when(userDetails.getPassword()).thenReturn("otra-clave");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            loginController.login(loginRequest);
        });

        assertEquals("Invalid login", exception.getMessage());
        verify(jwtAuthenticationConfig, never()).getJWTToken(anyString());
    }

    @Test
    void loginRequest_gettersAndSetters_workCorrectly() {
        LoginController.LoginRequest request = new LoginController.LoginRequest();

        request.setUsername("usuario1");
        request.setPassword("clave1");

        assertEquals("usuario1", request.getUsername());
        assertEquals("clave1", request.getPassword());
    }
}