package com.humberto.versiculos.service;

import com.humberto.versiculos.model.Versiculo;
import com.humberto.versiculos.repository.VersiculoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VersiculoService {

    private final VersiculoRepository repository;

    public VersiculoService(VersiculoRepository repository) {
        this.repository = repository;
    }

    public Versiculo criarVersiculo(Versiculo versiculo) {
        if (versiculo.getTipo() == null) {
            throw new NullPointerException("Tipo não pode ser nulo");
        }
        if (versiculo.getReferenciaBiblica() == null) {
            throw new NullPointerException("Referência Bíblica não pode ser nula");
        }
        return repository.save(versiculo);
    }

    public List<Versiculo> listarTodos() {
        return repository.findAll();
    }

    public List<Versiculo> buscarPorData(LocalDate data) {
        return repository.findByData(data);
    }

    public Optional<Versiculo> atualizarVersiculo(Long id, Versiculo novoVersiculo) {
        return repository.findById(id).map(v -> {
            v.setTipo(novoVersiculo.getTipo());
            v.setReferenciaBiblica(novoVersiculo.getReferenciaBiblica());
            v.setConteudo(novoVersiculo.getConteudo());
            v.setData(novoVersiculo.getData());
            return repository.save(v);
        });
    }

    public void deletarVersiculo(Long id) {
        repository.deleteById(id);
    }
}
