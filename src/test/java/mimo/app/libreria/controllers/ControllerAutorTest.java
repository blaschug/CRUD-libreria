package mimo.app.libreria.controllers;

import mimo.app.libreria.repositorio.RepositoryAutor;
import mimo.app.libreria.servicios.ServiceAutor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MenuController.class)
public class ControllerAutorTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceAutor serviceAutor;

    @MockBean
    private RepositoryAutor repositoryAutor;

    @Test
    public void iniciaElIndex_retorna200() throws Exception {
        mockMvc.perform(get("/")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void seleccionaBuscarEnIndex_retorna200() throws Exception {
        mockMvc.perform(get("/buscar")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    // Luego de estos test no supe como seguir
}
