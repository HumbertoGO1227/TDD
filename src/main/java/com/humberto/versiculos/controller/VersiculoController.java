package com.humberto.versiculos.controller;

import com.humberto.versiculos.model.Versiculo;
import com.humberto.versiculos.service.VersiculoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/versiculos")
public class VersiculoController {

    private final VersiculoService service;

    public VersiculoController(VersiculoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Versiculo> criar(@RequestBody Versiculo versiculo) {
        Versiculo salvo = service.criarVersiculo(versiculo);
        return new ResponseEntity<>(salvo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Versiculo>> listar() {
        List<Versiculo> versiculos = service.listarTodos();
        return ResponseEntity.ok(versiculos);
    }

    @GetMapping("/data/{data}")
    public ResponseEntity<List<Versiculo>> buscarPorData(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<Versiculo> versiculos = service.buscarPorData(data);
        return ResponseEntity.ok(versiculos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Versiculo> atualizar(@PathVariable Long id, @RequestBody Versiculo versiculo) {
        return service.atualizarVersiculo(id, versiculo)
                .map(v -> new ResponseEntity<>(v, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletarVersiculo(id);
        return ResponseEntity.noContent().build();
    }
}
