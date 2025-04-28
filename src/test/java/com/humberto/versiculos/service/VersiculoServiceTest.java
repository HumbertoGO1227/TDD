package com.humberto.versiculos.service;

import com.humberto.versiculos.model.TipoVersiculo;
import com.humberto.versiculos.model.Versiculo;
import com.humberto.versiculos.repository.VersiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VersiculoServiceTest {

    private VersiculoService service;
    private VersiculoRepository repository;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(VersiculoRepository.class);
        service = new VersiculoService(repository);
    }

    @Test
    void deveCriarUmVersiculo() {
        Versiculo versiculo = new Versiculo();
        versiculo.setTipo(TipoVersiculo.EVANGELHO);
        versiculo.setReferenciaBiblica("João 3:16");
        versiculo.setConteudo("Porque Deus amou o mundo...");
        versiculo.setData(LocalDate.now());

        when(repository.save(versiculo)).thenReturn(versiculo);

        Versiculo salvo = service.criarVersiculo(versiculo);

        assertEquals("João 3:16", salvo.getReferenciaBiblica());
    }

    @Test
    void deveListarVersiculos() {
        service.listarTodos();
        verify(repository).findAll();
    }

    @Test
    void deveBuscarPorData() {
        LocalDate data = LocalDate.now();
        service.buscarPorData(data);
        verify(repository).findByData(data);
    }

    @Test
    void deveAtualizarVersiculo() {
        Versiculo versiculo = new Versiculo();
        versiculo.setId(1L);
        versiculo.setTipo(TipoVersiculo.EVANGELHO);

        when(repository.findById(1L)).thenReturn(Optional.of(versiculo));
        when(repository.save(any())).thenReturn(versiculo);

        Optional<Versiculo> atualizado = service.atualizarVersiculo(1L, versiculo);

        assertTrue(atualizado.isPresent());
    }

    @Test
    void deveDeletarVersiculo() {
        service.deletarVersiculo(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void naoDeveAtualizarQuandoIdNaoExistir() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        Optional<Versiculo> resultado = service.atualizarVersiculo(999L, new Versiculo());

        assertTrue(resultado.isEmpty());
    }

    @Test
    void naoDeveSalvarVersiculoSemReferencia() {
        Versiculo versiculo = new Versiculo();
        versiculo.setTipo(TipoVersiculo.EVANGELHO);
        versiculo.setConteudo("Conteúdo");
        versiculo.setData(LocalDate.now());

        assertThrows(NullPointerException.class, () -> {
            service.criarVersiculo(versiculo);
        });
    }

    @Test
    void naoDeveSalvarVersiculoSemTipo() {
        Versiculo versiculo = new Versiculo();
        versiculo.setReferenciaBiblica("Salmos 23:1");
        versiculo.setConteudo("O Senhor é meu pastor.");
        versiculo.setData(LocalDate.now());

        assertThrows(NullPointerException.class, () -> {
            service.criarVersiculo(versiculo);
        });
    }
}
