package com.humberto.versiculos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.humberto.versiculos.model.TipoVersiculo;
import com.humberto.versiculos.model.Versiculo;
import com.humberto.versiculos.service.VersiculoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VersiculoController.class)
class VersiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VersiculoService versiculoService;

    private Versiculo versiculo;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        versiculo = new Versiculo();
        versiculo.setId(1L);
        versiculo.setTipo(TipoVersiculo.EVANGELHO);
        versiculo.setReferenciaBiblica("João 3:16");
        versiculo.setConteudo("Porque Deus amou o mundo...");
        versiculo.setData(LocalDate.now());
    }

    @Test
    void deveCriarVersiculo() throws Exception {
        Mockito.when(versiculoService.criarVersiculo(any(Versiculo.class))).thenReturn(versiculo);

        mockMvc.perform(post("/versiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(versiculo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.tipo").value("EVANGELHO"));
    }

    @Test
    void deveListarVersiculos() throws Exception {
        Mockito.when(versiculoService.listarTodos()).thenReturn(Collections.singletonList(versiculo));

        mockMvc.perform(get("/versiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void deveBuscarVersiculoPorData() throws Exception {
        Mockito.when(versiculoService.buscarPorData(any(LocalDate.class))).thenReturn(Collections.singletonList(versiculo));

        mockMvc.perform(get("/versiculos/data")
                        .param("data", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void deveAtualizarVersiculo() throws Exception {
        Mockito.when(versiculoService.atualizarVersiculo(eq(1L), any(Versiculo.class))).thenReturn(Optional.of(versiculo));

        mockMvc.perform(put("/versiculos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(versiculo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void deveDeletarVersiculo() throws Exception {
        mockMvc.perform(delete("/versiculos/1"))
                .andExpect(status().isNoContent());
    }
}
