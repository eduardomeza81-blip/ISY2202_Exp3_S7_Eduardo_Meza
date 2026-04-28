package com.duoc.seguridadcalidad;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HomeControllerTest {

    @Test
    void home_shouldReturnHomeViewAndAddTitle() {
        HomeController controller = new HomeController();
        Model model = mock(Model.class);

        String view = controller.home(model);

        assertEquals("home", view);
        verify(model).addAttribute("titulo", "Recetas de cocina");
    }

    @Test
    void buscar_shouldReturnBuscarViewAndAddTitle() {
        HomeController controller = new HomeController();
        Model model = mock(Model.class);

        String view = controller.buscar(model);

        assertEquals("buscar", view);
        verify(model).addAttribute("titulo", "Buscar recetas");
    }

    @Test
    void detalle_shouldReturnDetalleViewAndAddRecipeId() {
        HomeController controller = new HomeController();
        Model model = mock(Model.class);

        String view = controller.detalle(10L, model);

        assertEquals("detalle", view);
        verify(model).addAttribute("idReceta", 10L);
    }
}