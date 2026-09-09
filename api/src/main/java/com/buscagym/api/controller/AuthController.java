package com.buscagym.api.controller;

import com.buscagym.api.dto.LoginDTO;
import com.buscagym.api.dto.RegistroDTO;
import com.buscagym.api.model.Usuario;
import com.buscagym.api.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsuarioRepository usuarioRepository;

    public AuthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody RegistroDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", "Este e-mail já está cadastrado.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
        }

        Usuario novoUsuario = new Usuario(dto.getNome(), dto.getEmail(), dto.getSenha());
        usuarioRepository.save(novoUsuario);

        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Usuário cadastrado com sucesso!");
        response.put("nome", novoUsuario.getNome());
        response.put("email", novoUsuario.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(dto.getEmail());

        if (usuarioOpt.isEmpty() || !usuarioOpt.get().getSenha().equals(dto.getSenha())) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", "E-mail ou senha incorretos.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
        }

        Usuario usuario = usuarioOpt.get();
        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Login realizado com sucesso!");
        response.put("id", usuario.getId());
        response.put("nome", usuario.getNome());
        response.put("email", usuario.getEmail());

        return ResponseEntity.ok(response);
    }
}