package com.autobots.automanager.cadastradores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;

@Component
public class VeiculoCadastro {

    @Autowired
    private VendaCadastro vendaCadastro;

    @Autowired
    private VeiculoRepositorio veiculoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private VendaRepositorio vendaRepositorio;

    public Veiculo cadastrarVeiculo (Veiculo veiculo) {
        Veiculo veiculoCadastrado = new Veiculo();
        veiculoCadastrado.setModelo(veiculo.getModelo());
        veiculoCadastrado.setPlaca(veiculo.getPlaca());
        veiculoCadastrado.setTipo(veiculo.getTipo());

        veiculoRepositorio.save(veiculoCadastrado);

        if (veiculo.getProprietario() != null) {
            Usuario proprietario = new Usuario();
            proprietario.setNome(veiculo.getProprietario().getNome());
            proprietario.getVeiculos().add(veiculoCadastrado);
            veiculoCadastrado.setProprietario(proprietario);
        }
        if (veiculo.getVendas() != null) {
            for (Venda venda : veiculo.getVendas()) {
                Venda vendaAtual = vendaCadastro.cadastrarVenda(venda);
                vendaAtual.setVeiculo(veiculoCadastrado);
                veiculoCadastrado.getVendas().add(vendaAtual);
            }
        }
        veiculoRepositorio.save(veiculoCadastrado);
        return veiculoCadastrado;
    }
}