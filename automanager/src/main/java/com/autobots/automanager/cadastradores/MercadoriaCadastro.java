package com.autobots.automanager.cadastradores;

import com.autobots.automanager.controles.dto.CadastradorMercadoriaDto;
import com.autobots.automanager.entidades.Mercadoria;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class MercadoriaCadastro {
    public Mercadoria cadastrarMercadoria (CadastradorMercadoriaDto mercadoria) {
        Mercadoria mercadoriaCadastrada = new Mercadoria();
        mercadoriaCadastrada.setValidade(new Date());
        mercadoriaCadastrada.setFabricao(new Date());
        mercadoriaCadastrada.setCadastro(new Date());
        mercadoriaCadastrada.setNome(mercadoria.nome());
        mercadoriaCadastrada.setQuantidade(mercadoria.quantidade());
        mercadoriaCadastrada.setValor(mercadoria.valor());
        if (mercadoria.descricao().isPresent()) {
            mercadoriaCadastrada.setDescricao(mercadoria.descricao().get());
        }
        return mercadoriaCadastrada;
    }

    public Mercadoria cadastrarMercadoria (Mercadoria mercadoria) {
        Mercadoria mercadoriaCadastrada = new Mercadoria();
        mercadoriaCadastrada.setValidade(new Date());
        mercadoriaCadastrada.setFabricao(new Date());
        mercadoriaCadastrada.setCadastro(new Date());
        mercadoriaCadastrada.setNome(mercadoria.getNome());
        mercadoriaCadastrada.setQuantidade(mercadoria.getQuantidade());
        mercadoriaCadastrada.setValor(mercadoria.getValor());
        if (mercadoria.getDescricao() != null) {
            mercadoriaCadastrada.setDescricao(mercadoria.getDescricao());
        }
        return mercadoriaCadastrada;
    }
}