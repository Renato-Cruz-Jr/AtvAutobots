package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.*;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/telefone/{id}")
    public List<Telefone> obterTelefone(@PathVariable long id) {
        List<Cliente> clientes = repositorio.findAll();
        return selecionador.selecionar(clientes, id).getTelefones();
    }

    @PostMapping("/cadastrar/{id}")
    public void cadastrarTelefone(@PathVariable long id, @RequestBody Telefone telefone) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        cadastrador.cadastrar(cliente, telefone);
        repositorio.save(cliente);
    }

    @PutMapping("/atualizar/{id}")
    public void atualizarTelefone(@PathVariable long id, @RequestBody List<Telefone> telefone) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        atualizador.atualizar(cliente.getTelefones(), telefone);
        repositorio.save(cliente);
    }

    @DeleteMapping("/excluir/{id}")
    public void excluirTelefone(@RequestBody List<Telefone> telefones, @PathVariable long id){
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        removedor.excluir(cliente, telefones);
        repositorio.save(cliente);
    }
}
