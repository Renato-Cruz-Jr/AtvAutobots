package com.autobots.automanager.controles.dto;
import java.util.Optional;

public record CadastradorCredencialDto(
    Optional<String> nomeUsuario,
    Optional<String> senha,
    Optional<Long> codigo
) {
}