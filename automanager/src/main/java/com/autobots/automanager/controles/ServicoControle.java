package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.services.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servico")
public class ServicoControle {

    @Autowired
    private ServicoService servicoService;

    @GetMapping("/listar")
    public ResponseEntity<List<Servico>> listarServicos() {
        List<Servico> servicos =  servicoService.listarServicos();
        if (servicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Servico> visualizarServico(@PathVariable Long id) {
        Servico servico = servicoService.visualizarServico(id);
        if (servico == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(servico);
    }

    @GetMapping("/visualizar/venda/{idVenda}")
    public ResponseEntity<List<Servico>> visualizarServicosVenda(@PathVariable Long idVenda) {
        List<Servico> servicos = servicoService.visualizarServicosVenda(idVenda);
        if (servicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/visualizar/empresa/{idEmpresa}")
    public ResponseEntity<List<Servico>> visualizarServicosEmpresa(@PathVariable Long idEmpresa) {
        List<Servico> servicos = servicoService.visualizarServicosEmpresa(idEmpresa);
        if (servicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(servicos);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarServico(@RequestBody Servico servico) {
        try {
            servicoService.cadastrarServico(servico);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar/empresa/{idEmpresa}")
    public ResponseEntity<?> cadastrarServicoEmpresa(@RequestBody Servico servico, @PathVariable Long idEmpresa) {
        try {
            servicoService.cadastrarServicoEmpresa(servico, idEmpresa);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar/venda/{idVenda}")
    public ResponseEntity<?> cadastrarServicoVenda(@RequestBody Servico servico, @PathVariable Long idVenda) {
        try {
            servicoService.cadastrarServicoVenda(servico, idVenda);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarServico(@PathVariable Long id, @RequestBody Servico servico) {
        try {
            servicoService.atualizarServico(id, servico);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/vincular/{idServico}/venda/{idVenda}")
    public ResponseEntity<?> vincularServicoVenda(@PathVariable Long idServico, @PathVariable Long idVenda) {
        try {
            servicoService.vincularServicoVenda(idServico, idVenda);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/desvincular/{idServico}/venda/{idVenda}")
    public ResponseEntity<?> desvincularServicoVenda(@PathVariable Long idServico, @PathVariable Long idVenda) {
        try {
            servicoService.desvincularServicoVenda(idServico, idVenda);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/vincular/{idServico}/empresa/{idEmpresa}")
    public ResponseEntity<?> vincularServicoEmpresa(@PathVariable Long idServico, @PathVariable Long idEmpresa) {
        try {
            servicoService.vincularServicoEmpresa(idServico, idEmpresa);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/desvincular/{idServico}/empresa/{idEmpresa}")
    public ResponseEntity<?> desvincularServicoEmpresa(@PathVariable Long idServico, @PathVariable Long idEmpresa) {
        try {
            servicoService.desvincularServicoEmpresa(idServico, idEmpresa);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarServico(@PathVariable Long id) {
        try {
            servicoService.deletarServico(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
