package com.buscagym.api.controller;

import com.buscagym.api.model.Academia;
import com.buscagym.api.repository.AcademiaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academias")
@CrossOrigin(origins = "*")
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

    // Este método é essencial para receber os cadastros do modal:
    @PostMapping
    public ResponseEntity<Academia> cadastrar(@RequestBody Academia academia) {
        if (academia.getPlanos() != null) {
            academia.getPlanos().forEach(plano -> plano.setAcademia(academia));
        }
        Academia novaAcademia = repository.save(academia);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAcademia);
    }
}