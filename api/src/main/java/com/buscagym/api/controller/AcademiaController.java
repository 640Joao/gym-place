package com.buscagym.api.controller;

import com.buscagym.api.model.Academia;
import com.buscagym.api.repository.AcademiaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academias")
@CrossOrigin(origins = "*") // Permite chamadas do front-end local
public class AcademiaController {

    private final AcademiaRepository repository;

    public AcademiaController(AcademiaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Academia> listarTodas() {
        return repository.findAll();
    }

    @GetMapping("/busca")
    public List<Academia> buscar(
            @RequestParam(required = false) String termo,
            @RequestParam(required = false) Double precoMax) {
        return repository.buscarPorFiltros(termo, precoMax);
    }
}