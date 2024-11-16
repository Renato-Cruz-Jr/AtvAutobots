package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.*;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import javax.swing.text.html.parser.Entity;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
    @Autowired
    private ClienteRepositorio repositorio;
    @Autowired
    private ClienteSelecionador selecionador;
    @Autowired
    private EnderecoAtualizador atualizador;
    @Autowired
    private EnderecoRepositorio enderecoRepositorio;
    @Autowired
    private AdicionadorLinkEndereco adicionadorLink;
    @Autowired
    private EnderecoSelecionador enderecoSelecionador;

    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Endereco> visualizarEndereco(@PathVariable long id) {
        List<Endereco> enderecos = enderecoRepositorio.findAll();
        Endereco endereco = enderecoSelecionador.selecionar(enderecos, id);
        if (endereco != null) {
            adicionadorLink.adicionarLink(endereco);
            ResponseEntity<Endereco> resposta = new ResponseEntity<>(endereco, HttpStatus.FOUND);
            return resposta;
        } else {
            ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Endereco>> listaEnderecos() {
        List<Endereco> enderecos = enderecoRepositorio.findAll();
        if (!enderecos.isEmpty()) {
            adicionadorLink.adicionarLink(enderecos);
            ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(enderecos, HttpStatus.FOUND);
            return resposta;
        } else {
            ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return resposta;
        }
    }

    @GetMapping("/cliente/{clienteid}")
    public ResponseEntity<Endereco> visualizarEnderecoPorCliente(@PathVariable long clienteid) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, clienteid);
        if (cliente != null) {
            Endereco endereco = cliente.getEndereco();
            adicionadorLink.adicionarLink(endereco);
            ResponseEntity<Endereco> resposta = new ResponseEntity<>(endereco, HttpStatus.FOUND);
            return resposta;
        } else {
            ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        }
    }

    @PutMapping("/atualizar/{clienteid}")
    public ResponseEntity<?> atualizarEndereco(@RequestBody Endereco endereco, @PathVariable long clienteid) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, clienteid);
        HttpStatus status = HttpStatus.OK;
        if (cliente != null) {
            atualizador.atualizar(cliente.getEndereco(), endereco);
            repositorio.save(cliente);
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }
}
