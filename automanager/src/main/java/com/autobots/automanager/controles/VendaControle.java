package com.autobots.automanager.controles;

import com.autobots.automanager.controles.dto.AtualizadorVendaDto;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.services.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/venda")
public class VendaControle {

    @Autowired
    private VendaService vendaService;

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR', 'CLIENTE')")
    @GetMapping("/listar")
    public ResponseEntity<List<Venda>> listarVendas(Authentication authentication) {
        String username = authentication.getName();
        List<Venda> vendas = vendaService.listarVendas(username);
        if (vendas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vendas);
    }

    public ResponseEntity<List<Venda>> listarVendas() {
        List<Venda> vendas = vendaService.listarVendas();
        if (vendas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vendas);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Venda> visualizarVenda(@PathVariable Long id) {
        Venda venda = vendaService.visualizarVenda(id);
        if (venda == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(venda);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @GetMapping("/visualizar/empresa/{idEmpresa}")
    public ResponseEntity<List<Venda>> listarVendasEmpresa(@PathVariable Long idEmpresa) {
        List<Venda> vendas = vendaService.visualizarVendasEmpresa(idEmpresa);
        if (vendas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vendas);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @GetMapping("/visualizar/usuario/{idUsuario}")
    public ResponseEntity<List<Venda>> listarVendasUsuario(@PathVariable Long idUsuario) {
        List<Venda> vendas = vendaService.visualizarVendasUsuario(idUsuario);
        if (vendas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vendas);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarVenda(@RequestBody Venda venda, Authentication authentication) {
        String username = authentication.getName();
        try {
            vendaService.cadastrarVenda(venda, username);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @PostMapping("/cadastrar/empresa/{idEmpresa}")
    public ResponseEntity<?> cadastrarVendaEmpresa(@PathVariable Long idEmpresa, @RequestBody Venda venda, Authentication authentication) {
        String username = authentication.getName();
        try {
            vendaService.cadastrarVendaEmpresa(idEmpresa, venda, username);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @PostMapping("/cadastrar/usuario/{idUsuario}/{tipoUsuario}")
    public ResponseEntity<?> cadastrarVendaUsuario(@PathVariable Long idUsuario, @RequestBody Venda venda, @PathVariable String tipoUsuario, Authentication authentication) {
        String username = authentication.getName();
        try {
            vendaService.cadastrarVendaUsuario(idUsuario, venda, tipoUsuario, username);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarVenda(@PathVariable Long id, @RequestBody AtualizadorVendaDto venda) {
        try {
            vendaService.atualizarVenda(id, venda);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @PutMapping("/vincular/{idVenda}/usuario/{idUsuario}/{tipoUsuario}")
    public ResponseEntity<?> vincularVendaUsuario(@PathVariable Long idVenda, @PathVariable Long idUsuario, @PathVariable String tipoUsuario) {
        try {
            vendaService.vincularVendaUsuario(idVenda, idUsuario, tipoUsuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @PutMapping("/vincular/{idVenda}/empresa/{idEmpresa}")
    public ResponseEntity<?> vincularVendaEmpresa(@PathVariable Long idVenda, @PathVariable Long idEmpresa) {
        try {
            vendaService.vincularVendaEmpresa(idVenda, idEmpresa);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @PutMapping("/desvincular/{idVenda}/usuario/{idUsuario}/{tipoUsuario}")
    public ResponseEntity<?> desvincularVendaUsuario(@PathVariable Long idVenda, @PathVariable Long idUsuario, @PathVariable String tipoUsuario) {
        try {
            vendaService.desvincularVendaUsuario(idVenda, idUsuario, tipoUsuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @PutMapping("/desvincular/{idVenda}/empresa/{idEmpresa}")
    public ResponseEntity<?> desvincularVendaEmpresa(@PathVariable Long idVenda, @PathVariable Long idEmpresa) {
        try {
            vendaService.desvincularVendaEmpresa(idVenda, idEmpresa);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarVenda(@PathVariable Long id) {
        try {
            vendaService.deletarVenda(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}