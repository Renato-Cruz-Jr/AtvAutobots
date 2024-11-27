package com.autobots.automanager.controles;

import com.autobots.automanager.controles.dto.CadastradorMercadoriaDto;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.services.MercadoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaControle {

    @Autowired
    private MercadoriaService mercadoriaService;

    @GetMapping("/listar")
    public ResponseEntity<List<Mercadoria>> listarMercadorias() {
        List<Mercadoria> mercadorias = mercadoriaService.listarMercadorias();
        if (mercadorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mercadorias);
    }

    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Mercadoria> visualizarMercadoria(@PathVariable Long id) {
        Mercadoria mercadoria = mercadoriaService.visualizarMercadoria(id);
        if (mercadoria == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mercadoria);
    }

    @GetMapping("/visualizar/empresa/{idEmpresa}")
    public ResponseEntity<List<Mercadoria>> visualizarMercadoriaEmpresa(@PathVariable Long idEmpresa) {
        List<Mercadoria> mercadorias = mercadoriaService.visualizarMercadoriaEmpresa(idEmpresa);
        if (mercadorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mercadorias);
    }

    @GetMapping("/visualizar/usuario/{idUsuario}")
    public ResponseEntity<List<Mercadoria>> visualizarMercadoriaUsuario(@PathVariable Long idUsuario) {
        List<Mercadoria> mercadorias = mercadoriaService.visualizarMercadoriaUsuario(idUsuario);
        if (mercadorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mercadorias);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarMercadoria(@RequestBody CadastradorMercadoriaDto mercadoria) {
        try {
            mercadoriaService.cadastrarMercadoria(mercadoria);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar/empresa/{idEmpresa}")
    public ResponseEntity<?> cadastrarMercadoriaEmpresa(@RequestBody CadastradorMercadoriaDto mercadoria, @PathVariable Long idEmpresa) {
        try {
            mercadoriaService.cadastrarMercadoriaEmpresa(mercadoria, idEmpresa);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar/usuario/{idUsuario}")
    public ResponseEntity<?> cadastrarMercadoriaUsuario(@RequestBody CadastradorMercadoriaDto mercadoria, @PathVariable Long idUsuario) {
        try {
            mercadoriaService.cadastrarMercadoriaUsuario(mercadoria, idUsuario);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarMercadoria(@PathVariable Long id, @RequestBody Mercadoria mercadoria) {
        try {
            mercadoriaService.atualizarMercadoria(id, mercadoria);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/vincular/{idMercadoria}/empresa/{idEmpresa}")
    public ResponseEntity<?> vincularMercadoriaEmpresa(@PathVariable Long idMercadoria, @PathVariable Long idEmpresa) {
        try {
            mercadoriaService.vincularMercadoriaEmpresa(idMercadoria, idEmpresa);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/vincular/{idMercadoria}/usuario/{idUsuario}")
    public ResponseEntity<?> vincularMercadoriaUsuario(@PathVariable Long idMercadoria, @PathVariable Long idUsuario) {
        try {
            mercadoriaService.vincularMercadoriaUsuario(idMercadoria, idUsuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/desvincular/{idMercadoria}/empresa/{idEmpresa}")
    public ResponseEntity<?> desvincularMercadoriaEmpresa(@PathVariable Long idMercadoria, @PathVariable Long idEmpresa) {
        try {
            mercadoriaService.desvincularMercadoriaEmpresa(idMercadoria, idEmpresa);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/desvincular/{idMercadoria}/usuario/{idUsuario}")
    public ResponseEntity<?> desvincularMercadoriaUsuario(@PathVariable Long idMercadoria, @PathVariable Long idUsuario) {
        try {
            mercadoriaService.desvincularMercadoriaUsuario(idMercadoria, idUsuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

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
