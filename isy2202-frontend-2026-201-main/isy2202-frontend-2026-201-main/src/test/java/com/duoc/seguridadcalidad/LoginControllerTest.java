package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginControllerTest {

    @Test
    void login_shouldReturnLoginView() {
        LoginController controller = new LoginController();

        String view = controller.login();

        assertEquals("login", view);
    }
}