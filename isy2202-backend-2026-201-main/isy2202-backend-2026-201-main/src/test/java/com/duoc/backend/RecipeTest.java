package com.duoc.backend;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeTest {

    @Test
    void testRecipeGettersAndSetters() {
        Recipe recipe = new Recipe();

        List<String> ingredients = Arrays.asList("Choclo", "Carne", "Cebolla");
        List<String> photos = Arrays.asList("foto1.jpg", "foto2.jpg");

        recipe.setId(1L);
        recipe.setName("Pastel de choclo");
        recipe.setCuisineType("Chilena");
        recipe.setCountryOfOrigin("Chile");
        recipe.setDifficulty("Media");
        recipe.setInstructions("Mezclar ingredientes y hornear");
        recipe.setCookTimeMinutes(45);
        recipe.setIngredients(ingredients);
        recipe.setPhotos(photos);

        assertEquals(1L, recipe.getId());
        assertEquals("Pastel de choclo", recipe.getName());
        assertEquals("Chilena", recipe.getCuisineType());
        assertEquals("Chile", recipe.getCountryOfOrigin());
        assertEquals("Media", recipe.getDifficulty());
        assertEquals("Mezclar ingredientes y hornear", recipe.getInstructions());
        assertEquals(45, recipe.getCookTimeMinutes());
        assertEquals(ingredients, recipe.getIngredients());
        assertEquals(photos, recipe.getPhotos());
    }
}