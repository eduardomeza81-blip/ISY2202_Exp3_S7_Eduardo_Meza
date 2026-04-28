package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AuthProxyControllerTest {

    private RestTemplate restTemplate;
    private AuthProxyController controller;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        controller = new AuthProxyController(restTemplate, "http://localhost:8081");
    }

    @Test
    void login_success_returnsBackendResponse() {
        LoginRequest request = new LoginRequest();
        request.setUsername("eduardo");
        request.setPassword("1234");

        when(restTemplate.exchange(
                eq("http://localhost:8081/login"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(ResponseEntity.ok("token123"));

        ResponseEntity<String> response = controller.login(request);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("token123", response.getBody());
    }

    @Test
    void profile_withoutAuthorization_returns401() {
        ResponseEntity<String> response = controller.profile(null);

        assertEquals(401, response.getStatusCode().value());
        assertEquals("Authorization token missing", response.getBody());
        verifyNoInteractions(restTemplate);
    }

    @Test
    void profile_withAuthorization_returnsBackendResponse() {
        when(restTemplate.exchange(
                eq("http://localhost:8081/api/secure/profile"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(ResponseEntity.ok("perfil ok"));

        ResponseEntity<String> response = controller.profile("Bearer token123");

        assertEquals(200, response.getStatusCode().value());
        assertEquals("perfil ok", response.getBody());
    }

    @Test
    void getRecipes_returnsBackendResponse() {
        when(restTemplate.exchange(
                eq("http://localhost:8081/recipes"),
                eq(HttpMethod.GET),
                isNull(),
                eq(String.class)
        )).thenReturn(ResponseEntity.ok("[]"));

        ResponseEntity<String> response = controller.getRecipes();

        assertEquals(200, response.getStatusCode().value());
        assertEquals("[]", response.getBody());
    }

    @Test
    void searchRecipes_withFilters_returnsBackendResponse() {
        when(restTemplate.exchange(
                contains("http://localhost:8081/recipes/search"),
                eq(HttpMethod.GET),
                isNull(),
                eq(String.class)
        )).thenReturn(ResponseEntity.ok("[recetas]"));

        ResponseEntity<String> response = controller.searchRecipes(
                "Pastel",
                "Chilena",
                "Choclo",
                "Chile",
                "Media"
        );

        assertEquals(200, response.getStatusCode().value());
        assertEquals("[recetas]", response.getBody());
    }

    @Test
    void getRecipeById_returnsBackendResponse() {
        when(restTemplate.exchange(
                eq("http://localhost:8081/recipes/1"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(ResponseEntity.ok("receta 1"));

        ResponseEntity<String> response = controller.getRecipeById(1L, "Bearer token123");

        assertEquals(200, response.getStatusCode().value());
        assertEquals("receta 1", response.getBody());
    }

    @Test
    void createRecipe_withoutAuthorization_returns401() {
        RecipeDto recipe = new RecipeDto();

        ResponseEntity<String> response = controller.createRecipe(null, recipe);

        assertEquals(401, response.getStatusCode().value());
        assertEquals("Authorization token missing", response.getBody());
        verifyNoInteractions(restTemplate);
    }

    @Test
    void createRecipe_withAuthorization_returnsBackendResponse() {
        RecipeDto recipe = new RecipeDto();
        recipe.setName("Pastel");

        when(restTemplate.exchange(
                eq("http://localhost:8081/recipes"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body("creado"));

        ResponseEntity<String> response = controller.createRecipe("Bearer token123", recipe);

        assertEquals(201, response.getStatusCode().value());
        assertEquals("creado", response.getBody());
    }
}