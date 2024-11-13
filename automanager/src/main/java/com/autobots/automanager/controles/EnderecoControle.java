package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.*;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
    @Autowired
    private ClienteRepositorio repositorio;

    @Autowired
    private ClienteSelecionador selecionador;

    @Autowired
    private EnderecoAtualizador atualizador;

    @GetMapping("/endereco/{id}")
    public Endereco obterEndereco(@PathVariable long id) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        return cliente.getEndereco();
    }

    @PutMapping("/atualizar/{id}")
    public void atualizarEndereco(@PathVariable long id, @RequestBody Endereco endereco) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        atualizador.atualizar(cliente.getEndereco(), endereco);
        repositorio.save(cliente);
    }
}