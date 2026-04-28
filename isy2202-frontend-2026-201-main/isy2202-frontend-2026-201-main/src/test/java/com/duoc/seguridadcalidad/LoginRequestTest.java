package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginRequestTest {

    @Test
    void testGettersAndSetters() {
        LoginRequest request = new LoginRequest();

        request.setUsername("eduardo");
        request.setPassword("1234");

        assertEquals("eduardo", request.getUsername());
        assertEquals("1234", request.getPassword());
    }
}