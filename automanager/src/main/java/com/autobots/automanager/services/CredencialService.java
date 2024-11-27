package com.autobots.automanager.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import com.autobots.automanager.adicionadoresLinks.AdicionadorLinkCredencial;
import com.autobots.automanager.controles.dto.CadastradorCredencialDto;
import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.CredencialCodigoBarra;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorios.CredencialRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredencialService {
    @Autowired
    private CredencialRepositorio credencialRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private AdicionadorLinkCredencial adicionadorLinkCredencial;

    public List<Credencial> listarCredenciais() {
        List<Credencial> credencials = credencialRepositorio.findActiveCredenciais();
        adicionadorLinkCredencial.adicionarLink(credencials);
        return credencials;
    }

    public Credencial visualizarCredencial(Long id) {
        Credencial credencial = credencialRepositorio.findActiveCredencialById(id).orElse(null);
        if (credencial != null) {
            adicionadorLinkCredencial.adicionarLink(credencial);
        }
        return credencial;
    }

    public List<Credencial> listarCredenciaisUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepositorio.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        Set<Credencial> credenciais = usuario.getCredenciais();
        List<Credencial> credenciaisLista = new ArrayList<>(credenciais);
        adicionadorLinkCredencial.adicionarLink(credenciaisLista);
        return credenciaisLista;
    }

    public void cadastrarCredencialUsuarioSenha(Long idUsuario, CadastradorCredencialDto credencial) {

        Usuario usuario = usuarioRepositorio.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        CredencialUsuarioSenha credencialUsuarioSenha = new CredencialUsuarioSenha();
        if (credencial.nomeUsuario().isPresent()) {
            credencialUsuarioSenha.setNomeUsuario(credencial.nomeUsuario().get());
        }
        if (credencial.senha().isPresent()) {
            credencialUsuarioSenha.setSenha(credencial.senha().get());
        }
        credencialUsuarioSenha.setInativo(false);
        credencialUsuarioSenha.setCriacao(new Date());
        credencialUsuarioSenha.setUltimoAcesso(new Date());
        credencialRepositorio.save(credencialUsuarioSenha);

        usuario.getCredenciais().add(credencialUsuarioSenha);
        usuarioRepositorio.save(usuario);
    }

    public void cadastrarCredencialCodigoBarra(Long idUsuario, CadastradorCredencialDto credencial) {

        Usuario usuario = usuarioRepositorio.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        CredencialCodigoBarra credencialCodigoBarra = new CredencialCodigoBarra();
        if (credencial.codigo().isPresent()) {
            credencialCodigoBarra.setCodigo(credencial.codigo().get());
        }
        credencialCodigoBarra.setInativo(false);
        credencialCodigoBarra.setCriacao(new Date());
        credencialCodigoBarra.setUltimoAcesso(new Date());
        credencialRepositorio.save(credencialCodigoBarra);

        usuario.getCredenciais().add(credencialCodigoBarra);
        usuarioRepositorio.save(usuario);
    }

    public void atualizarCredencial(Long id, CadastradorCredencialDto credencial) {
        Credencial credencialAtualizado = credencialRepositorio.findActiveCredencialById(id).orElse(null);

        if (credencialAtualizado != null) {
            if (credencialAtualizado instanceof CredencialUsuarioSenha) {
                if (credencial.nomeUsuario().isPresent()) {
                    ((CredencialUsuarioSenha) credencialAtualizado).setNomeUsuario(credencial.nomeUsuario().get());
                }
                if (credencial.senha().isPresent()) {
                    ((CredencialUsuarioSenha) credencialAtualizado).setSenha(credencial.senha().get());
                }
            }
            else if (credencialAtualizado instanceof CredencialCodigoBarra) {
                if (credencial.codigo().isPresent()) {
                    ((CredencialCodigoBarra) credencialAtualizado).setCodigo(credencial.codigo().get());
                }
            }
            credencialAtualizado.setUltimoAcesso(new Date());

            credencialRepositorio.save(credencialAtualizado);
        }

        else {
            throw new IllegalArgumentException("Credencial não encontrada.");
        }
    }

    public void deletarCredencial(Long id) {
        Credencial credencial = credencialRepositorio.findById(id).orElse(null);
        if (credencial != null) {
            credencial.setInativo(true);
            credencialRepositorio.save(credencial);
        }
        else {
            throw new IllegalArgumentException("A credencial não foi encontrada.");
        }
    }
}
