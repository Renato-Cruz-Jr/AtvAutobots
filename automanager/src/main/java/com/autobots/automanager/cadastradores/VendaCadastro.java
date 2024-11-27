package com.autobots.automanager.cadastradores;

import java.util.Date;
import java.util.Optional;
import com.autobots.automanager.controles.dto.CadastradorMercadoriaDto;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VendaCadastro {
    @Autowired
    private MercadoriaCadastro mercadoriaCadastro;

    @Autowired
    private
    MercadoriaRepositorio mercadoriaRepositorio;

    @Autowired
    private UsuarioCadastro usuarioCadastro;

    public Venda cadastrarVenda(Venda venda) {
        Venda vendaCadastrada = new Venda();
        vendaCadastrada.setCadastro(new Date());
        vendaCadastrada.setIdentificacao(venda.getIdentificacao());
        if (venda.getCliente() != null) {
            Usuario cliente = usuarioCadastro.cadastrarUsuario(venda.getCliente());
            vendaCadastrada.setCliente(cliente);
        }
        if (venda.getFuncionario() != null) {
            Usuario funcionario = usuarioCadastro.cadastrarUsuario(venda.getFuncionario());
            vendaCadastrada.setFuncionario(funcionario);
        }
        if (venda.getMercadorias() != null) {
            for (Mercadoria mercadoria : venda.getMercadorias()) {
                CadastradorMercadoriaDto cadastradorMercadoriaDto = new CadastradorMercadoriaDto(
                        mercadoria.getNome(),
                        mercadoria.getQuantidade(),
                        mercadoria.getValor(),
                        Optional.ofNullable(mercadoria.getDescricao())
                );
                Mercadoria mercadoriaAtual = mercadoriaCadastro.cadastrarMercadoria(cadastradorMercadoriaDto);
                mercadoriaRepositorio.save(mercadoriaAtual);
                vendaCadastrada.getMercadorias().add(mercadoriaAtual);
            }
        }
        if (venda.getServicos() != null) {
            for (Servico servico : venda.getServicos()) {
                vendaCadastrada.getServicos().add(servico);
            }
        }
        if (venda.getVeiculo() != null) {
            vendaCadastrada.setVeiculo(venda.getVeiculo());
        }
        return vendaCadastrada;
    }
}