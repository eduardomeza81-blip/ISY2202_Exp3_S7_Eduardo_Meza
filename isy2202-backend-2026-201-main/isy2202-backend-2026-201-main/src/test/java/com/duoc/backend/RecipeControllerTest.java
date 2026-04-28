package com.duoc.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeControllerTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeController recipeController;

    private Recipe recipe;

    @BeforeEach
    void setUp() {
        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Pastel de choclo");
        recipe.setCuisineType("Chilena");
        recipe.setCountryOfOrigin("Chile");
        recipe.setDifficulty("Media");
        recipe.setInstructions("Mezclar y hornear");
        recipe.setCookTimeMinutes(45);
        recipe.setIngredients(Arrays.asList("Choclo", "Carne", "Cebolla"));
    }

    @Test
    void getAllRecipes_returnsAllRecipes() {
        when(recipeRepository.findAll()).thenReturn(List.of(recipe));

        Iterable<Recipe> result = recipeController.getAllRecipes();

        assertNotNull(result);
        assertEquals(1, ((List<Recipe>) result).size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void getRecipeById_whenExists_returnsRecipe() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        ResponseEntity<Recipe> response = recipeController.getRecipeById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Pastel de choclo", response.getBody().getName());
    }

    @Test
    void getRecipeById_whenNotExists_returnsNotFound() {
        when(recipeRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<Recipe> response = recipeController.getRecipeById(99L);

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void createRecipe_whenIdIsNull_createsRecipe() {
        Recipe newRecipe = new Recipe();
        newRecipe.setName("Empanadas");

        when(recipeRepository.save(newRecipe)).thenReturn(newRecipe);

        ResponseEntity<Recipe> response = recipeController.createRecipe(newRecipe);

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Empanadas", response.getBody().getName());
    }

    @Test
    void createRecipe_whenIdExists_setsIdToNullBeforeSaving() {
        Recipe newRecipe = new Recipe();
        newRecipe.setId(99L);
        newRecipe.setName("Cazuela");

        Recipe savedRecipe = new Recipe();
        savedRecipe.setName("Cazuela");

        when(recipeRepository.save(any(Recipe.class))).thenReturn(savedRecipe);

        ResponseEntity<Recipe> response = recipeController.createRecipe(newRecipe);

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertNull(newRecipe.getId());
        verify(recipeRepository, times(1)).save(newRecipe);
    }

    @Test
    void deleteRecipe_whenExists_returnsNoContent() {
        when(recipeRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<Void> response = recipeController.deleteRecipe(1L);

        assertEquals(204, response.getStatusCode().value());
        verify(recipeRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteRecipe_whenNotExists_returnsNotFound() {
        when(recipeRepository.existsById(99L)).thenReturn(false);

        ResponseEntity<Void> response = recipeController.deleteRecipe(99L);

        assertEquals(404, response.getStatusCode().value());
        verify(recipeRepository, never()).deleteById(99L);
    }

    @Test
    void searchRecipes_withoutFilters_returnsAll() {
        when(recipeRepository.findAll()).thenReturn(List.of(recipe));

        Iterable<Recipe> result = recipeController.searchRecipes(null, null, null, null, null);

        assertNotNull(result);
        assertEquals(1, ((List<Recipe>) result).size());
    }

    @Test
    void searchRecipes_byName_returnsFilteredRecipes() {
        when(recipeRepository.findAll()).thenReturn(List.of(recipe));
        when(recipeRepository.findByNameContainingIgnoreCase("Pastel")).thenReturn(List.of(recipe));

        Iterable<Recipe> result = recipeController.searchRecipes("Pastel", null, null, null, null);

        assertEquals(1, ((List<Recipe>) result).size());
    }

    @Test
    void searchRecipes_byIngredients_returnsFilteredRecipes() {
        when(recipeRepository.findAll()).thenReturn(List.of(recipe));

        Iterable<Recipe> result = recipeController.searchRecipes(
                null, null, null, null, Arrays.asList("Choclo", "Carne")
        );

        assertEquals(1, ((List<Recipe>) result).size());
    }

    @Test
    void searchRecipes_byIngredients_whenNoMatch_returnsEmptyList() {
        when(recipeRepository.findAll()).thenReturn(List.of(recipe));

        Iterable<Recipe> result = recipeController.searchRecipes(
                null, null, null, null, Collections.singletonList("Pollo")
        );

        assertEquals(0, ((List<Recipe>) result).size());
    }
}