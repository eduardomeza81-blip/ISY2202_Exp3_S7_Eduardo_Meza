package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RecipeDtoTest {

    @Test
    void testGettersAndSetters() {
        RecipeDto dto = new RecipeDto();

        dto.setName("Pastel");
        dto.setCuisineType("Chilena");
        dto.setCountryOfOrigin("Chile");
        dto.setDifficulty("Media");

        assertEquals("Pastel", dto.getName());
        assertEquals("Chilena", dto.getCuisineType());
        assertEquals("Chile", dto.getCountryOfOrigin());
        assertEquals("Media", dto.getDifficulty());
    }
}