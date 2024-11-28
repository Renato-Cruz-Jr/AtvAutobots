package com.autobots.automanager.controles;

import com.autobots.automanager.controles.dto.CadastradorCredencialDto;
import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.services.CredencialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credencial")
public class CredencialControle {
    @Autowired
    private CredencialService credencialService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/listar")
    public ResponseEntity<List<Credencial>> listarCredenciais() {
        List<Credencial> credenciais = credencialService.listarCredenciais();
        if (credenciais.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(credenciais);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/visualizar/{id}")
    public ResponseEntity<?> visualizarCredencial(@PathVariable Long id) {
        Credencial credencial = credencialService.visualizarCredencial(id);
        if (credencial == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(credencial);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR', 'CLIENTE')")
    @GetMapping("/usuario/{idUsuario}/listar")
    public ResponseEntity<?> listarCredenciaisUsuario(@PathVariable Long idUsuario, Authentication authentication) {
        String username = authentication.getName();
        try {
            Credencial credencial = credencialService.listarCredenciaisUsuario(idUsuario, username);
            if (credencial != null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(credencial);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @PostMapping("/cadastrar/{idUsuario}")
    public ResponseEntity<?> cadastrarCredencial(@PathVariable Long idUsuario, @RequestBody CadastradorCredencialDto credencial, Authentication authentication) {
        String username = authentication.getName();
        try {
            credencialService.cadastrarCredencial(idUsuario, credencial, username);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarCredencial(@PathVariable Long id, @RequestBody CadastradorCredencialDto credencial, Authentication authentication) {
        String username = authentication.getName();
        try {
            credencialService.atualizarCredencial(id, credencial, username);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarCredencial(@PathVariable Long id) {
        try {
            credencialService.deletarCredencial(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}