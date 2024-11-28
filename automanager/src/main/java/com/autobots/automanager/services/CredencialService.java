package com.autobots.automanager.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import com.autobots.automanager.adicionadoresLinks.AdicionadorLinkCredencial;
import com.autobots.automanager.controles.dto.CadastradorCredencialDto;
import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorios.CredencialRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.utilitarios.UsuarioSelecionador;
import com.autobots.automanager.utilitarios.VerificadorPermissao;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CredencialService {
    @Autowired
    private CredencialRepositorio credencialRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private UsuarioSelecionador usuarioSelecionador;

    @Autowired
    private VerificadorPermissao verificadorPermissao;

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

    public Credencial listarCredenciaisUsuario(Long idUsuario, String usuarioNome) {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        Usuario usuario = usuarioRepositorio.findById(idUsuario).orElse(null);
        Usuario usuarioSelecionado = usuarioSelecionador.selecionarUsuarioNome(usuarios, usuarioNome);
        if (usuario == null) {
            throw new IllegalArgumentException("O usuário não foi encontrado.");
        }
        boolean permitido = verificadorPermissao.verificar(usuarioSelecionado.getPerfis(), usuario.getPerfis());
        if (!permitido) {
            throw new IllegalArgumentException("O usuário não tem permissão.");
        }
        Credencial credencial = usuario.getCredencial();
        adicionadorLinkCredencial.adicionarLink(credencial);
        return credencial;
    }

    public void cadastrarCredencial (Long idUsuario, CadastradorCredencialDto credencial, String usuarioNome) {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        Usuario usuarioSelecionado = usuarioSelecionador.selecionarUsuarioNome(usuarios, usuarioNome);
        Usuario usuario = usuarioRepositorio.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("O usuário não foi encontrado.");
        }
        boolean permitido = verificadorPermissao.verificar(usuarioSelecionado.getPerfis(), usuario.getPerfis());
        if (!permitido) {
            throw new IllegalArgumentException("Usuário sem permissão.");
        }
        Credencial cadastroCrendencial = new Credencial();
        if (credencial.nomeUsuario().isPresent()) {
            cadastroCrendencial.setNomeUsuario(credencial.nomeUsuario().get());
        }
        if (credencial.senha().isPresent()) {
            cadastroCrendencial.setSenha(credencial.senha().get());
        }
        cadastroCrendencial.setInativo(false);
        credencialRepositorio.save(cadastroCrendencial);
        usuario.setCredencial(cadastroCrendencial);
        usuarioRepositorio.save(usuario);
    }

    public void atualizarCredencial(Long id, CadastradorCredencialDto credencial, String usuarioNome) {
        Credencial credencialAtualizado = credencialRepositorio.findActiveCredencialById(id).orElse(null);
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        Usuario usuarioSelecionado = usuarioSelecionador.selecionarUsuarioNome(usuarios, usuarioNome);
        Usuario usuarioAtualizado = null;
        for (Usuario usuario : usuarios) {
            if (usuario.getCredencial().getId().equals(id)) {
                usuarioAtualizado = usuario;
                break;
            }
        }
        if (usuarioAtualizado == null) {
            throw new IllegalArgumentException("O usuário não foi encontrado.");
        }
        boolean permitido = verificadorPermissao.verificar(usuarioSelecionado.getPerfis(), usuarioAtualizado.getPerfis());
        if (!permitido) {
            throw new IllegalArgumentException("O usuário não tem permissão.");
        }
        if (credencialAtualizado != null) {
            if (credencial.nomeUsuario().isPresent()) {
                credencialAtualizado.setNomeUsuario(credencial.nomeUsuario().get());
            }
            if (credencial.senha().isPresent()) {
                credencialAtualizado.setSenha(credencial.senha().get());
            }
            credencialRepositorio.save(credencialAtualizado);
        }
        else {
            throw new IllegalArgumentException("A credencial não foi encontrada.");
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