package com.autobots.automanager.controles;

import com.autobots.automanager.controles.dto.CadastradorCredencialDto;
import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.services.CredencialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credencial")
public class CredencialControle {

    @Autowired
    private CredencialService credencialService;

    @GetMapping("/listar")
    public ResponseEntity<List<Credencial>> listarCredenciais() {
        List<Credencial> credenciais = credencialService.listarCredenciais();
        if (credenciais.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(credenciais);
    }

    @GetMapping("/visualizar/{id}")
    public ResponseEntity<?> visualizarCredencial(@PathVariable Long id) {
        Credencial credencial = credencialService.visualizarCredencial(id);
        if (credencial == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(credencial);
    }

    @GetMapping("/usuario/{idUsuario}/listar")
    public ResponseEntity<List<Credencial>> listarCredenciaisUsuario(@PathVariable Long idUsuario) {
        List<Credencial> credenciais = credencialService.listarCredenciaisUsuario(idUsuario);
        if (credenciais.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(credenciais);
    }

    @PostMapping("/cadastrar/{idUsuario}/{tipoCredencial}")
    public ResponseEntity<?> cadastrarCredencial(@PathVariable Long idUsuario, @PathVariable String tipoCredencial, @RequestBody CadastradorCredencialDto credencial) {
        if (tipoCredencial.equalsIgnoreCase("usuariosenha")) {
            try {
                credencialService.cadastrarCredencialUsuarioSenha(idUsuario, credencial);
                return ResponseEntity.created(null).build();
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
        else if (tipoCredencial.equalsIgnoreCase("codigobarra")) {
            try {
                credencialService.cadastrarCredencialCodigoBarra(idUsuario, credencial);
                return ResponseEntity.created(null).build();
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de credencial inválido.");
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarCredencial(@PathVariable Long id, @RequestBody CadastradorCredencialDto credencial) {
        try {
            credencialService.atualizarCredencial(id, credencial);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

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
