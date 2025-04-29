package com.humberto.versiculos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.humberto.versiculos.model.TipoVersiculo;
import com.humberto.versiculos.model.Versiculo;
import com.humberto.versiculos.service.VersiculoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VersiculoController.class)
class VersiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VersiculoService versiculoService;

    @Test
    void deveCriarVersiculo() throws Exception {
        Versiculo versiculo = criarVersiculo();
        when(versiculoService.criarVersiculo(any(Versiculo.class))).thenReturn(versiculo);

        mockMvc.perform(post("/versiculos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(versiculo)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveAtualizarVersiculo() throws Exception {
        Versiculo versiculo = criarVersiculo();
        when(versiculoService.atualizarVersiculo(any(Long.class), any(Versiculo.class))).thenReturn(Optional.of(versiculo));

        mockMvc.perform(put("/versiculos/{id}", 1L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(versiculo)))
                .andExpect(status().isOk());
    }

    @Test
    void deveListarVersiculos() throws Exception {
        when(versiculoService.listarTodos()).thenReturn(Collections.singletonList(criarVersiculo()));

        mockMvc.perform(get("/versiculos"))
                .andExpect(status().isOk());
    }

    @Test
    void deveBuscarVersiculoPorData() throws Exception {
        when(versiculoService.buscarPorData(any(LocalDate.class))).thenReturn(Collections.singletonList(criarVersiculo()));

        mockMvc.perform(get("/versiculos/data/{data}", LocalDate.now().toString()))
                .andExpect(status().isOk());
    }

    @Test
    void deveDeletarVersiculo() throws Exception {
        doNothing().when(versiculoService).deletarVersiculo(any(Long.class));

        mockMvc.perform(delete("/versiculos/{id}", 1L))
                .andExpect(status().isNoContent());
    }


    private Versiculo criarVersiculo() {
        Versiculo versiculo = new Versiculo();
        versiculo.setId(1L);
        versiculo.setTipo(TipoVersiculo.EVANGELHO);
        versiculo.setReferenciaBiblica("João 3:16");
        versiculo.setConteudo("Porque Deus amou o mundo...");
        versiculo.setData(LocalDate.now());
        return versiculo;
    }
}
