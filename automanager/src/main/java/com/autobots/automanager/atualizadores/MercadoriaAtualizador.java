package com.autobots.automanager.atualizadores;

import com.autobots.automanager.entidades.Mercadoria;
import org.springframework.stereotype.Component;

@Component
public class MercadoriaAtualizador {

    public Mercadoria atualizarMercadoria(Mercadoria mercadoria, Mercadoria novaMercadoria) {
        mercadoria.setNome(novaMercadoria.getNome());
        mercadoria.setQuantidade(novaMercadoria.getQuantidade());
        mercadoria.setValor(novaMercadoria.getValor());
        mercadoria.setDescricao(novaMercadoria.getDescricao());
        return mercadoria;
    }
}