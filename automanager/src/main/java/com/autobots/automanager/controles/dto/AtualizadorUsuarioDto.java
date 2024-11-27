package com.autobots.automanager.controles.dto;

import java.util.Optional;
import java.util.Set;
import com.autobots.automanager.entidades.*;
import com.autobots.automanager.enumeracoes.PerfilUsuario;

public record AtualizadorUsuarioDto(
        Optional<String> nome,
        Optional<String> nomeSocial,
        Optional<Set<PerfilUsuario>> perfis,
        Optional<Set<Telefone>> telefones,
        Optional<Endereco> endereco,
        Optional<Set<Documento>> documentos,
        Optional<Set<Email>> emails,
        Optional<Set<CredencialUsuarioSenha>> credenciais,
        Optional<Set<Mercadoria>> mercadorias,
        Optional<Set<Venda>> vendas,
        Optional<Set<Veiculo>> veiculos
) {
}