package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresa")
public class EmpresaControle {
    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/listar")
    public ResponseEntity<List<Empresa>> listarEmpresas() {
        List<Empresa> empresas = empresaService.listarEmpresas();
        if (empresas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Empresa> visualizarEmpresa(@PathVariable Long id) {
        Empresa empresa = empresaService.visualizarEmpresa(id);
        if (empresa == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(empresa);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarEmpresa(@RequestBody Empresa empresa) {
        try {
            empresaService.cadastrarEmpresa(empresa);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarEmpresa(@PathVariable Long id, @RequestBody Empresa empresa) {
        ResponseEntity<?> resposta = empresaService.atualizarEmpresa(id, empresa);
        return resposta;
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarEmpresa(@PathVariable Long id) {
        try {
            empresaService.deletarEmpresa(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

