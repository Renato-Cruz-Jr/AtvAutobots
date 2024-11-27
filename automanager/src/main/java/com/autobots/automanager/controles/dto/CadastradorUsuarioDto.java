package com.autobots.automanager.controles.dto;

import java.util.Set;
import com.autobots.automanager.entidades.*;
import com.autobots.automanager.enumeracoes.PerfilUsuario;

public record CadastradorUsuarioDto(
        String nome,
        String nomeSocial,
        Set<PerfilUsuario> perfis,
        Set<Telefone> telefones,
        Endereco endereco,
        Set<Documento> documentos,
        Set<Email> emails,
        Set<CredencialUsuarioSenha> credenciais,
        Set<Mercadoria> mercadorias,
        Set<Venda> vendas,
        Set<Veiculo> veiculos
        ) {
}
