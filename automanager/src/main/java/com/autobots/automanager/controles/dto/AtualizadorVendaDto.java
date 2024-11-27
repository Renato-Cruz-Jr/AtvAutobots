package com.autobots.automanager.controles.dto;

import java.util.Optional;
import java.util.Set;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;

public record AtualizadorVendaDto(
        Optional<String> identificacao,
        Optional<Usuario> cliente,
        Optional<Usuario> funcionario,
        Optional<Set<Mercadoria>> mercadorias,
        Optional<Set<Servico>> servicos,
        Optional<Veiculo> veiculo

) {
}