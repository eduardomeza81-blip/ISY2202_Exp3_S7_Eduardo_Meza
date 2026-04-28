package com.duoc.backend;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void testUserGettersAndSetters() {
        User user = new User();

        user.setId(1);
        user.setUsername("eduardo");
        user.setEmail("test@test.com");
        user.setPassword("1234");

        assertEquals(1, user.getId());
        assertEquals("eduardo", user.getUsername());
        assertEquals("test@test.com", user.getEmail());
        assertEquals("1234", user.getPassword());
    }
}