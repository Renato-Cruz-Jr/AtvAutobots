package com.autobots.automanager.controles;

import com.autobots.automanager.adaptadores.UserDetailsServiceImpl;
import com.autobots.automanager.controles.dto.AtualizadorUsuarioDto;
import com.autobots.automanager.controles.dto.CadastradorCredencialDto;
import com.autobots.automanager.controles.dto.CadastradorUsuarioDto;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.services.UsuarioService;
import com.autobots.automanager.utilitarios.UsuarioSelecionador;
import com.autobots.automanager.utilitarios.VerificadorPermissao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioControle {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private VerificadorPermissao verificadorPermissao;

    @Autowired
    private UsuarioSelecionador usuarioSelecionador;

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR', 'CLIENTE')")
    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios(Authentication authentication) {
        String username = authentication.getName();
        List<Usuario> usuarios = usuarioService.listarUsuarios(username);

        if (usuarios.isEmpty()) {
            ResponseEntity<List<Usuario>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
            ResponseEntity<List<Usuario>> resposta = new ResponseEntity<>(usuarios, HttpStatus.FOUND);
            return resposta;
        }
    }

    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();

        if (usuarios.isEmpty()) {
            ResponseEntity<List<Usuario>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
            ResponseEntity<List<Usuario>> resposta = new ResponseEntity<>(usuarios, HttpStatus.FOUND);
            return resposta;
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Usuario> visualizarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.visualizarUsuario(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody CadastradorUsuarioDto usuario, Authentication authentication) {
        String username = authentication.getName();
        try {
            usuarioService.cadastrarUsuario(username, usuario);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @PostMapping("/cadastrar/empresa/{idEmpresa}")
    public ResponseEntity<?> cadastrarUsuarioEmpresa(@RequestBody CadastradorUsuarioDto usuario, @PathVariable Long idEmpresa, Authentication authentication) {
        String username = authentication.getName();
        try {
            usuarioService.cadastrarUsuarioEmpresa(usuario, idEmpresa, username);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @PutMapping("/vincular/{idUsuario}/empresa/{idEmpresa}")
    public ResponseEntity<?> vincularUsuarioEmpresa(@PathVariable Long idUsuario, @PathVariable Long idEmpresa, Authentication authentication) {
        String username = authentication.getName();
        try {
            usuarioService.vincularUsuarioEmpresa(idUsuario, idEmpresa, username);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @PutMapping("/desvincular/{idUsuario}/empresa/{idEmpresa}")
    public ResponseEntity<?> desvincularUsuarioEmpresa(@PathVariable Long idUsuario, @PathVariable Long idEmpresa, Authentication authentication) {
        String username = authentication.getName();
        try {
            usuarioService.desvincularUsuarioEmpresa(idUsuario, idEmpresa, username);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @RequestBody AtualizadorUsuarioDto usuario, Authentication authentication) {
        String username = authentication.getName();
        ResponseEntity<?> resposta = usuarioService.atualizarUsuario(id, usuario, username);
        return resposta;
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        try {
            usuarioService.deletarUsuario(id, username);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}