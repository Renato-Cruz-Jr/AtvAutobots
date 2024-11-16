package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.*;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
    @Autowired
    private ClienteRepositorio repositorio;
    @Autowired
    private ClienteSelecionador selecionador;
    @Autowired
    private TelefoneCadastrador cadastrador;
    @Autowired
    private TelefoneAtualizador atualizador;
    @Autowired
    private TelefoneRemovedor removedor;
    @Autowired
    private TelefoneRepositorio telefoneRepositorio;
    @Autowired
    private TelefoneSelecionador telefoneSelecionador;
    @Autowired
    private AdicionadorLinkTelefone adicionadorLink;

    @PostMapping("/cadastrar/{clienteid}")
    public ResponseEntity<?> cadastrarTelefone(@RequestBody List<Telefone> telefones, @PathVariable long clienteid) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, clienteid);
        HttpStatus status = HttpStatus.CREATED;
        if (cliente != null) {
            cadastrador.cadastrar(cliente, telefones);
            repositorio.save(cliente);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(status);
    }

    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Telefone> visualizarTelefone(@PathVariable long id) {
        List<Telefone> telefones = telefoneRepositorio.findAll();
        Telefone telefone = telefoneSelecionador.selecionar(telefones, id);
        if (telefone != null) {
            adicionadorLink.adicionarLink(telefone);
            ResponseEntity<Telefone> resposta = new ResponseEntity<>(telefone, HttpStatus.FOUND);
            return resposta;
        } else {
            ResponseEntity<Telefone> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Telefone>> listarTelefones() {
        List<Telefone> telefones = telefoneRepositorio.findAll();
        if (!telefones.isEmpty()) {
            adicionadorLink.adicionarLink(telefones);
            ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(telefones, HttpStatus.FOUND);
            return resposta;
        } else {
            ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        }
    }

    @GetMapping("/cliente/{clienteid}")
    public ResponseEntity<List<Telefone>> visualizarTelefonesPorCliente(@PathVariable long clienteid) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, clienteid);
        if (cliente != null) {
            adicionadorLink.adicionarLink(cliente.getTelefones());
            ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(cliente.getTelefones(), HttpStatus.FOUND);
            return resposta;
        } else {
            ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        }
    }

    @PutMapping("/atualizar/{clienteid}")
    public ResponseEntity<?> atualizarTelefone(@RequestBody List<Telefone> telefones, @PathVariable long clienteid) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, clienteid);
        HttpStatus status = HttpStatus.OK;
        if (cliente != null) {
            atualizador.atualizar(cliente.getTelefones(), telefones);
            repositorio.save(cliente);
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/excluir/{clienteid}")
    public ResponseEntity<?> excluirTelefone(@RequestBody List<Telefone> telefone, @PathVariable long clienteid) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, clienteid);
        HttpStatus status = HttpStatus.OK;
        if (cliente != null) {
            removedor.excluir(cliente, telefone);
            repositorio.save(cliente);
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }
}