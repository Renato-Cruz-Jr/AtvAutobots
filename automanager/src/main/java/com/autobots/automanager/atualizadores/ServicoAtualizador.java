package com.autobots.automanager.atualizadores;

import com.autobots.automanager.entidades.Servico;
import org.springframework.stereotype.Component;

@Component
public class ServicoAtualizador {
    public Servico atualizarServico(Servico servico, Servico servicoAtualizado) {
        servico.setNome(servicoAtualizado.getNome());
        servico.setValor(servicoAtualizado.getValor());
        servico.setDescricao(servicoAtualizado.getDescricao());
        return servico;
    }
}