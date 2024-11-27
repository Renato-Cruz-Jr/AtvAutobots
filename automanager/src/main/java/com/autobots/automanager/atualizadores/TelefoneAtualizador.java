package com.autobots.automanager.atualizadores;

import com.autobots.automanager.entidades.Telefone;
import org.springframework.stereotype.Component;

@Component
public class TelefoneAtualizador {
    public Telefone atualizarTelefone(Telefone telefone, Telefone novoTelefone) {
        telefone.setDdd(novoTelefone.getDdd());
        telefone.setNumero(novoTelefone.getNumero());
        return telefone;
    }
}