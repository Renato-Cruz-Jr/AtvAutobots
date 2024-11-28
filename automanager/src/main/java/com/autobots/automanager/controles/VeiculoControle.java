package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.services.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/veiculo")
public class VeiculoControle {

    @Autowired
    private VeiculoService veiculoService;

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @GetMapping("/listar")
    public ResponseEntity<List<Veiculo>> listarVeiculos() {
        List<Veiculo> veiculos =  veiculoService.listarVeiculos();
        if (veiculos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(veiculos);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Veiculo> visualizarVeiculo(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        try {
            Veiculo veiculo = veiculoService.visualizarVeiculo(id, username);
            if (veiculo == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(veiculo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    public ResponseEntity<Veiculo> visualizarVeiculo(@PathVariable Long id) {
        Veiculo veiculo = veiculoService.visualizarVeiculo(id);
        if (veiculo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(veiculo);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @GetMapping("/usuario/{idUsuario}/listar")
    public ResponseEntity<List<Veiculo>> listarVeiculosUsuario(@PathVariable Long idUsuario, Authentication authentication) {
        String username = authentication.getName();
        try {
            List<Veiculo> veiculos = veiculoService.listarVeiculosUsuario(idUsuario, username);
            if (veiculos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(veiculos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarVeiculo(@RequestBody Veiculo veiculo) {
        try {
            veiculoService.cadastrarVeiculo(veiculo);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarVeiculo(@PathVariable Long id, @RequestBody Veiculo veiculo, Authentication authentication) {
        String username = authentication.getName();
        try {
            veiculoService.atualizarVeiculo(id, veiculo, username);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @PutMapping("/vincular/{idVeiculo}/usuario/{idUsuario}")
    public ResponseEntity<?> vincularVeiculoUsuario(@PathVariable Long idVeiculo, @PathVariable Long idUsuario, Authentication authentication) {
        String username = authentication.getName();
        try {
            ResponseEntity<?> resposta = veiculoService.vincularVeiculoUsuario(idVeiculo, idUsuario, username);
            return resposta;
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @PutMapping("/desvincular/{idVeiculo}/usuario/{idUsuario}")
    public ResponseEntity<?> desvincularVeiculoUsuario(@PathVariable Long idVeiculo, @PathVariable Long idUsuario, Authentication authentication) {
        String username = authentication.getName();
        try {
            ResponseEntity<?> resposta = veiculoService.desvincularVeiculoUsuario(idVeiculo, idUsuario, username);
            return resposta;
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> excluirVeiculo(@PathVariable Long id) {
        try {
            ResponseEntity<?> resposta = veiculoService.excluirVeiculo(id);
            return resposta;
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}