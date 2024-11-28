package com.autobots.automanager.controles;

import com.autobots.automanager.controles.dto.CadastradorMercadoriaDto;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.services.MercadoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaControle {

    @Autowired
    private MercadoriaService mercadoriaService;

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE', 'VENDEDOR')")
    @GetMapping("/listar")
    public ResponseEntity<List<Mercadoria>> listarMercadorias() {
        List<Mercadoria> mercadorias = mercadoriaService.listarMercadorias();
        if (mercadorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mercadorias);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE', 'VENDEDOR')")
    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Mercadoria> visualizarMercadoria(@PathVariable Long id) {
        Mercadoria mercadoria = mercadoriaService.visualizarMercadoria(id);
        if (mercadoria == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mercadoria);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE', 'VENDEDOR')")
    @GetMapping("/visualizar/empresa/{idEmpresa}")
    public ResponseEntity<List<Mercadoria>> visualizarMercadoriaEmpresa(@PathVariable Long idEmpresa) {
        List<Mercadoria> mercadorias = mercadoriaService.visualizarMercadoriaEmpresa(idEmpresa);
        if (mercadorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mercadorias);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE', 'VENDEDOR')")
    @GetMapping("/visualizar/usuario/{idUsuario}")
    public ResponseEntity<List<Mercadoria>> visualizarMercadoriaUsuario(@PathVariable Long idUsuario) {
        List<Mercadoria> mercadorias = mercadoriaService.visualizarMercadoriaUsuario(idUsuario);
        if (mercadorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mercadorias);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarMercadoria(@RequestBody CadastradorMercadoriaDto mercadoria) {
        try {
            mercadoriaService.cadastrarMercadoria(mercadoria);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PostMapping("/cadastrar/empresa/{idEmpresa}")
    public ResponseEntity<?> cadastrarMercadoriaEmpresa(@RequestBody CadastradorMercadoriaDto mercadoria, @PathVariable Long idEmpresa) {
        try {
            mercadoriaService.cadastrarMercadoriaEmpresa(mercadoria, idEmpresa);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PostMapping("/cadastrar/usuario/{idUsuario}")
    public ResponseEntity<?> cadastrarMercadoriaUsuario(@RequestBody CadastradorMercadoriaDto mercadoria, @PathVariable Long idUsuario) {
        try {
            mercadoriaService.cadastrarMercadoriaUsuario(mercadoria, idUsuario);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarMercadoria(@PathVariable Long id, @RequestBody Mercadoria mercadoria) {
        try {
            mercadoriaService.atualizarMercadoria(id, mercadoria);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PutMapping("/vincular/{idMercadoria}/empresa/{idEmpresa}")
    public ResponseEntity<?> vincularMercadoriaEmpresa(@PathVariable Long idMercadoria, @PathVariable Long idEmpresa) {
        try {
            mercadoriaService.vincularMercadoriaEmpresa(idMercadoria, idEmpresa);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PutMapping("/vincular/{idMercadoria}/usuario/{idUsuario}")
    public ResponseEntity<?> vincularMercadoriaUsuario(@PathVariable Long idMercadoria, @PathVariable Long idUsuario) {
        try {
            mercadoriaService.vincularMercadoriaUsuario(idMercadoria, idUsuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PutMapping("/desvincular/{idMercadoria}/empresa/{idEmpresa}")
    public ResponseEntity<?> desvincularMercadoriaEmpresa(@PathVariable Long idMercadoria, @PathVariable Long idEmpresa) {
        try {
            mercadoriaService.desvincularMercadoriaEmpresa(idMercadoria, idEmpresa);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PutMapping("/desvincular/{idMercadoria}/usuario/{idUsuario}")
    public ResponseEntity<?> desvincularMercadoriaUsuario(@PathVariable Long idMercadoria, @PathVariable Long idUsuario) {
        try {
            mercadoriaService.desvincularMercadoriaUsuario(idMercadoria, idUsuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarMercadoria(@PathVariable Long id) {
        try {
            mercadoriaService.deletarMercadoria(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}