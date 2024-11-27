package com.autobots.automanager.controles;

import com.autobots.automanager.controles.dto.AtualizadorUsuarioDto;
import com.autobots.automanager.controles.dto.CadastradorUsuarioDto;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioControle {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Usuario> visualizarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.visualizarUsuario(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody CadastradorUsuarioDto usuario) {
        try {
            usuarioService.cadastrarUsuario(usuario);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar/empresa/{idEmpresa}")
    public ResponseEntity<?> cadastrarUsuarioEmpresa(@RequestBody CadastradorUsuarioDto usuario, @PathVariable Long idEmpresa) {
        try {
            usuarioService.cadastrarUsuarioEmpresa(usuario, idEmpresa);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/vincular/{idUsuario}/empresa/{idEmpresa}")
    public ResponseEntity<?> vincularUsuarioEmpresa(@PathVariable Long idUsuario, @PathVariable Long idEmpresa) {
        try {
            usuarioService.vincularUsuarioEmpresa(idUsuario, idEmpresa);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/desvincular/{idUsuario}/empresa/{idEmpresa}")
    public ResponseEntity<?> desvincularUsuarioEmpresa(@PathVariable Long idUsuario, @PathVariable Long idEmpresa) {
        try {
            usuarioService.desvincularUsuarioEmpresa(idUsuario, idEmpresa);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @RequestBody AtualizadorUsuarioDto usuario) {
        ResponseEntity<?> resposta = usuarioService.atualizarUsuario(id, usuario);
        return resposta;
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {
        try {
            usuarioService.deletarUsuario(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
