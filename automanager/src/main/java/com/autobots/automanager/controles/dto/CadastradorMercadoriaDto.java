package com.autobots.automanager.controles.dto;
import java.util.Optional;

public record CadastradorMercadoriaDto(
    String nome,
    long quantidade,
    double valor,
    Optional<String> descricao
){
}