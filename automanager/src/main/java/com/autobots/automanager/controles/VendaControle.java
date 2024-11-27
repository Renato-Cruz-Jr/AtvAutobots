package com.autobots.automanager.controles;

import com.autobots.automanager.controles.dto.AtualizadorVendaDto;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.services.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venda")
public class VendaControle {

    @Autowired
    private VendaService vendaService;

    @GetMapping("/listar")
    public ResponseEntity<List<Venda>> listarVendas() {
        List<Venda> vendas = vendaService.listarVendas();
        if (vendas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Venda> visualizarVenda(@PathVariable Long id) {
        Venda venda = vendaService.visualizarVenda(id);
        if (venda == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(venda);
    }

    @GetMapping("/visualizar/empresa/{idEmpresa}")
    public ResponseEntity<List<Venda>> listarVendasEmpresa(@PathVariable Long idEmpresa) {
        List<Venda> vendas = vendaService.visualizarVendasEmpresa(idEmpresa);
        if (vendas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/visualizar/usuario/{idUsuario}")
    public ResponseEntity<List<Venda>> listarVendasUsuario(@PathVariable Long idUsuario) {
        List<Venda> vendas = vendaService.visualizarVendasUsuario(idUsuario);
        if (vendas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vendas);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarVenda(@RequestBody Venda venda) {
        try {
            vendaService.cadastrarVenda(venda);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar/empresa/{idEmpresa}")
    public ResponseEntity<?> cadastrarVendaEmpresa(@PathVariable Long idEmpresa, @RequestBody Venda venda) {
        try {
            vendaService.cadastrarVendaEmpresa(idEmpresa, venda);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar/usuario/{idUsuario}/{tipoUsuario}")
    public ResponseEntity<?> cadastrarVendaUsuario(@PathVariable Long idUsuario, @RequestBody Venda venda, @PathVariable String tipoUsuario) {
        try {
            vendaService.cadastrarVendaUsuario(idUsuario, venda, tipoUsuario);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarVenda(@PathVariable Long id, @RequestBody AtualizadorVendaDto venda) {
        try {
            vendaService.atualizarVenda(id, venda);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/vincular/{idVenda}/usuario/{idUsuario}/{tipoUsuario}")
    public ResponseEntity<?> vincularVendaUsuario(@PathVariable Long idVenda, @PathVariable Long idUsuario, @PathVariable String tipoUsuario) {
        try {
            vendaService.vincularVendaUsuario(idVenda, idUsuario, tipoUsuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/vincular/{idVenda}/empresa/{idEmpresa}")
    public ResponseEntity<?> vincularVendaEmpresa(@PathVariable Long idVenda, @PathVariable Long idEmpresa) {
        try {
            vendaService.vincularVendaEmpresa(idVenda, idEmpresa);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/desvincular/{idVenda}/usuario/{idUsuario}/{tipoUsuario}")
    public ResponseEntity<?> desvincularVendaUsuario(@PathVariable Long idVenda, @PathVariable Long idUsuario, @PathVariable String tipoUsuario) {
        try {
            vendaService.desvincularVendaUsuario(idVenda, idUsuario, tipoUsuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/desvincular/{idVenda}/empresa/{idEmpresa}")
    public ResponseEntity<?> desvincularVendaEmpresa(@PathVariable Long idVenda, @PathVariable Long idEmpresa) {
        try {
            vendaService.desvincularVendaEmpresa(idVenda, idEmpresa);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

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
