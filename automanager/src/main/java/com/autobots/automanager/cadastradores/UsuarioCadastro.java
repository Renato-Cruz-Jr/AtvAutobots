package com.autobots.automanager.cadastradores;

import java.util.Date;
import java.util.Set;
import com.autobots.automanager.controles.dto.CadastradorUsuarioDto;
import com.autobots.automanager.entidades.*;
import org.springframework.stereotype.Component;

@Component
public class UsuarioCadastro {
    public Usuario cadastrarUsuario(CadastradorUsuarioDto usuario) {
        Usuario usuarioCadastrado = new Usuario();
        usuarioCadastrado.setNome(usuario.nome());
        usuarioCadastrado.setNomeSocial(usuario.nomeSocial());
        usuarioCadastrado.setPerfis(usuario.perfis());

        for (Telefone telefone : usuario.telefones()) {
            Telefone telefoneCadastrado = new Telefone();
            telefoneCadastrado.setDdd(telefone.getDdd());
            telefoneCadastrado.setNumero(telefone.getNumero());
            usuarioCadastrado.getTelefones().add(telefoneCadastrado);
        }

        Endereco enderecoCadastrado = new Endereco();
        enderecoCadastrado.setEstado(usuario.endereco().getEstado());
        enderecoCadastrado.setCidade(usuario.endereco().getCidade());
        enderecoCadastrado.setBairro(usuario.endereco().getBairro());
        enderecoCadastrado.setRua(usuario.endereco().getRua());
        enderecoCadastrado.setNumero(usuario.endereco().getNumero());
        enderecoCadastrado.setCodigoPostal(usuario.endereco().getCodigoPostal());
        if (usuario.endereco().getInformacoesAdicionais() != null) {
            enderecoCadastrado.setInformacoesAdicionais(usuario.endereco().getInformacoesAdicionais());
        }
        usuarioCadastrado.setEndereco(enderecoCadastrado);

        for (Documento documento : usuario.documentos()) {
            Documento documentoCadastrado = new Documento();
            documentoCadastrado.setDataEmissao(new Date());
            documentoCadastrado.setTipo(documento.getTipo());
            documentoCadastrado.setNumero(documento.getNumero());
            usuarioCadastrado.getDocumentos().add(documentoCadastrado);
        }

        for (Email email : usuario.emails()) {
            Email emailCadastrado = new Email();
            emailCadastrado.setEndereco(email.getEndereco());
            usuarioCadastrado.getEmails().add(emailCadastrado);
        }

        Set<CredencialUsuarioSenha> credenciaisUsuarioSenha = usuario.credenciais();

        for (CredencialUsuarioSenha credencialUsuarioSenha : credenciaisUsuarioSenha) {
            CredencialUsuarioSenha credencial = new CredencialUsuarioSenha();
            credencial.setNomeUsuario(credencialUsuarioSenha.getNomeUsuario());
            credencial.setSenha(credencialUsuarioSenha.getSenha());
            credencial.setInativo(false);
            credencial.setCriacao(new Date());
            credencial.setUltimoAcesso(new Date());
            usuarioCadastrado.getCredenciais().add(credencial);
        }

        if (usuario.mercadorias() != null) {
            for (Mercadoria mercadoria : usuario.mercadorias()) {
                Mercadoria mercadoriaCadastrada = new Mercadoria();
                mercadoriaCadastrada.setValidade(new Date());
                mercadoriaCadastrada.setFabricao(new Date());
                mercadoriaCadastrada.setNome(mercadoria.getNome());
                mercadoriaCadastrada.setCadastro(new Date());
                mercadoriaCadastrada.setQuantidade(mercadoria.getQuantidade());
                mercadoriaCadastrada.setValor(mercadoria.getValor());
                mercadoriaCadastrada.setDescricao(mercadoria.getDescricao());
                usuarioCadastrado.getMercadorias().add(mercadoriaCadastrada);
            }
        }

        if (usuario.vendas() != null) {
            usuarioCadastrado.setVendas(usuario.vendas());
        }

        if (usuario.veiculos() != null) {
            usuarioCadastrado.setVeiculos(usuario.veiculos());
        }

        return usuarioCadastrado;
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        Usuario usuarioCadastrado = new Usuario();
        usuarioCadastrado.setNome(usuario.getNome());
        usuarioCadastrado.setNomeSocial(usuario.getNomeSocial());
        usuarioCadastrado.setPerfis(usuario.getPerfis());

        for (Telefone telefone : usuario.getTelefones()) {
            Telefone telefoneCadastrado = new Telefone();
            telefoneCadastrado.setDdd(telefone.getDdd());
            telefoneCadastrado.setNumero(telefone.getNumero());
            usuarioCadastrado.getTelefones().add(telefoneCadastrado);
        }

        Endereco enderecoCadastrado = new Endereco();
        enderecoCadastrado.setEstado(usuario.getEndereco().getEstado());
        enderecoCadastrado.setCidade(usuario.getEndereco().getCidade());
        enderecoCadastrado.setBairro(usuario.getEndereco().getBairro());
        enderecoCadastrado.setRua(usuario.getEndereco().getRua());
        enderecoCadastrado.setNumero(usuario.getEndereco().getNumero());
        enderecoCadastrado.setCodigoPostal(usuario.getEndereco().getCodigoPostal());
        if (usuario.getEndereco().getInformacoesAdicionais() != null) {
            enderecoCadastrado.setInformacoesAdicionais(usuario.getEndereco().getInformacoesAdicionais());
        }
        usuarioCadastrado.setEndereco(enderecoCadastrado);

        for (Documento documento : usuario.getDocumentos()) {
            Documento documentoCadastrado = new Documento();
            documentoCadastrado.setDataEmissao(new Date());
            documentoCadastrado.setTipo(documento.getTipo());
            documentoCadastrado.setNumero(documento.getNumero());
            usuarioCadastrado.getDocumentos().add(documentoCadastrado);
        }

        for (Email email : usuario.getEmails()) {
            Email emailCadastrado = new Email();
            emailCadastrado.setEndereco(email.getEndereco());
            usuarioCadastrado.getEmails().add(emailCadastrado);
        }

        for (Credencial credencial : usuario.getCredenciais()) {
            if (credencial instanceof CredencialUsuarioSenha) {
                CredencialUsuarioSenha credencialCadastrada = new CredencialUsuarioSenha();
                CredencialUsuarioSenha original = (CredencialUsuarioSenha) credencial;
                credencialCadastrada.setNomeUsuario(original.getNomeUsuario());
                credencialCadastrada.setSenha(original.getSenha());
                credencialCadastrada.setInativo(false);
                credencialCadastrada.setCriacao(new Date());
                credencialCadastrada.setUltimoAcesso(new Date());
                usuarioCadastrado.getCredenciais().add(credencialCadastrada);
            } else if (credencial instanceof CredencialCodigoBarra) {
                CredencialCodigoBarra credencialCadastrada = new CredencialCodigoBarra();
                CredencialCodigoBarra original = (CredencialCodigoBarra) credencial;
                credencialCadastrada.setCodigo(original.getCodigo());
                credencialCadastrada.setInativo(false);
                credencialCadastrada.setCriacao(new Date());
                credencialCadastrada.setUltimoAcesso(new Date());
                usuarioCadastrado.getCredenciais().add(credencialCadastrada);
            }
        }

        if (usuario.getMercadorias() != null) {
            for (Mercadoria mercadoria : usuario.getMercadorias()) {
                Mercadoria mercadoriaCadastrada = new Mercadoria();
                mercadoriaCadastrada.setValidade(new Date());
                mercadoriaCadastrada.setFabricao(new Date());
                mercadoriaCadastrada.setNome(mercadoria.getNome());
                mercadoriaCadastrada.setCadastro(new Date());
                mercadoriaCadastrada.setQuantidade(mercadoria.getQuantidade());
                mercadoriaCadastrada.setValor(mercadoria.getValor());
                mercadoriaCadastrada.setDescricao(mercadoria.getDescricao());
                usuarioCadastrado.getMercadorias().add(mercadoriaCadastrada);
            }
        }

        if (usuario.getVendas() != null) {
            usuarioCadastrado.setVendas(usuario.getVendas());
        }

        if (usuario.getVeiculos() != null) {
            usuarioCadastrado.setVeiculos(usuario.getVeiculos());
        }

        return usuarioCadastrado;
    }
}