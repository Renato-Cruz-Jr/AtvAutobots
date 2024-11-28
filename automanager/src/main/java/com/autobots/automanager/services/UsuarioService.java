package com.autobots.automanager.services;

import com.autobots.automanager.adicionadoresLinks.AdicionadorLinkUsuario;
import com.autobots.automanager.atualizadores.DocumentoAtualizador;
import com.autobots.automanager.atualizadores.EmailAtualizador;
import com.autobots.automanager.atualizadores.TelefoneAtualizador;
import com.autobots.automanager.cadastradores.UsuarioCadastro;
import com.autobots.automanager.controles.dto.AtualizadorUsuarioDto;
import com.autobots.automanager.controles.dto.CadastradorUsuarioDto;
import com.autobots.automanager.entidades.*;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.repositorios.*;
import com.autobots.automanager.utilitarios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private AdicionadorLinkUsuario adicionadorLinkUsuario;

    @Autowired
    private RepositorioEmpresa repositorioEmpresa;

    @Autowired
    private VendaRepositorio vendaRepositorio;

    @Autowired
    private VeiculoRepositorio veiculoRepositorio;

    @Autowired
    private UsuarioCadastro usuarioCadastro;

    @Autowired
    private DocumentoAtualizador documentoAtualizador;

    @Autowired
    private TelefoneAtualizador telefoneAtualizador;

    @Autowired
    private EmailAtualizador emailAtualizador;

    @Autowired
    private UsuarioSelecionador usuarioSelecionador;

    @Autowired
    private VerificadorPermissao verificadorPermissao;

    public List<Usuario> listarUsuarios(String usuarioNome) {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        Usuario usuarioLogado = usuarioSelecionador.selecionarUsuarioNome(usuarios, usuarioNome);

        if (usuarioLogado.getPerfis().contains(PerfilUsuario.ROLE_GERENTE)) {
            usuarios = usuarioSelecionador.selecionarPorCargo(usuarios, PerfilUsuario.ROLE_GERENTE);
        } else if (usuarioLogado.getPerfis().contains(PerfilUsuario.ROLE_VENDEDOR)) {
            usuarios = usuarioSelecionador.selecionarPorCargo(usuarios, PerfilUsuario.ROLE_VENDEDOR);
        } else if (usuarioLogado.getPerfis().contains(PerfilUsuario.ROLE_CLIENTE)) {
            usuarios = new ArrayList<Usuario>();
            usuarios.add(usuarioLogado);
        }
        adicionadorLinkUsuario.adicionarLink(usuarios);
        return usuarios;
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        adicionadorLinkUsuario.adicionarLink(usuarios);
        return usuarios;
    }

    public Usuario visualizarUsuario(Long id) {
        Usuario usuario = usuarioRepositorio.findById(id).orElse(null);
        if (usuario != null) {
            adicionadorLinkUsuario.adicionarLink(usuario);
        }
        return usuario;
    }

    public void cadastrarUsuario(String usuarioNome, CadastradorUsuarioDto usuario) {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        Usuario usuarioLogado = usuarioSelecionador.selecionarUsuarioNome(usuarios, usuarioNome);

        boolean permitido = verificadorPermissao.verificar(usuarioLogado.getPerfis(),usuario.perfis());

        if (!permitido) {
            throw new IllegalArgumentException("Usuário sem permissão para cadastrar usuário");
        }

        Usuario usuarioCadastrado = usuarioCadastro.cadastrarUsuario(usuario);
        usuarioRepositorio.save(usuarioCadastrado);
    }

    public void cadastrarUsuarioEmpresa(CadastradorUsuarioDto usuario, Long idEmpresa, String usuarioNome) {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        Usuario usuarioLogado = usuarioSelecionador.selecionarUsuarioNome(usuarios, usuarioNome);

        boolean permitido = verificadorPermissao.verificar(usuarioLogado.getPerfis(),usuario.perfis());

        if (!permitido) {
            throw new IllegalArgumentException("Usuário sem permissão para cadastrar usuário");
        }

        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        Usuario usuarioCadastrado = usuarioCadastro.cadastrarUsuario(usuario);
        Usuario usuarioSalvo = usuarioRepositorio.save(usuarioCadastrado);
        empresa.getUsuarios().add(usuarioSalvo);
        repositorioEmpresa.save(empresa);
    }

    public void vincularUsuarioEmpresa(Long idUsuario, Long idEmpresa, String usuarioNome) {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        Usuario usuarioLogado = usuarioSelecionador.selecionarUsuarioNome(usuarios, usuarioNome);
        Usuario usuario = usuarioRepositorio.findById(idUsuario).orElse(null);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        boolean permitido = verificadorPermissao.verificar(usuarioLogado.getPerfis(), usuario.getPerfis());

        if (!permitido) {
            throw new IllegalArgumentException("Usuário sem permissão para cadastrar usuário");
        }

        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        empresa.getUsuarios().add(usuario);
        repositorioEmpresa.save(empresa);
    }

    public void desvincularUsuarioEmpresa(Long idUsuario, Long idEmpresa, String usuarioNome) {

        List<Usuario> usuarios = usuarioRepositorio.findAll();
        Usuario usuarioLogado = usuarioSelecionador.selecionarUsuarioNome(usuarios, usuarioNome);
        Usuario usuario = usuarioRepositorio.findById(idUsuario).orElse(null);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        boolean permitido = verificadorPermissao.verificar(usuarioLogado.getPerfis(), usuario.getPerfis());

        if (!permitido) {
            throw new IllegalArgumentException("Usuário sem permissão para cadastrar usuário");
        }

        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        empresa.getUsuarios().remove(usuario);
        repositorioEmpresa.save(empresa);
    }

    public ResponseEntity<?> atualizarUsuario(Long id, AtualizadorUsuarioDto usuario, String usuarioNome) {

        List<Usuario> usuarios = usuarioRepositorio.findAll();
        Usuario usuarioLogado = usuarioSelecionador.selecionarUsuarioNome(usuarios, usuarioNome);
        Usuario usuarioAtual = usuarioRepositorio.findById(id).orElse(null);


        if (usuarioAtual != null) {

            boolean permitido = verificadorPermissao.verificar(usuarioLogado.getPerfis(), usuarioAtual.getPerfis());

            if (!permitido) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            if (usuario.nome().isPresent()) {
                usuarioAtual.setNome(usuario.nome().get());
            }
            if (usuario.nomeSocial().isPresent()) {
                usuarioAtual.setNomeSocial(usuario.nomeSocial().get());
            }
            if (usuario.perfis().isPresent()) {
                usuarioAtual.setPerfis(usuario.perfis().get());
            }
            if (usuario.telefones().isPresent()) {
                for (Telefone telefone : usuario.telefones().get()) {
                    for (Telefone telefoneAtual : usuarioAtual.getTelefones()) {
                        if (telefone.getId().equals(telefoneAtual.getId())) {
                            telefoneAtualizador.atualizarTelefone(telefoneAtual, telefone);
                        }
                    }
                }
            }
            if (usuario.endereco().isPresent()) {
                usuarioAtual.setEndereco(usuario.endereco().get());
            }
            if (usuario.documentos().isPresent()) {
                for (Documento documento : usuario.documentos().get()) {
                    for (Documento documentoAtual : usuarioAtual.getDocumentos()) {
                        if (documento.getId().equals(documentoAtual.getId())) {
                            documentoAtualizador.atualizarDocumento(documentoAtual, documento);
                        }
                    }
                }
            }
            if (usuario.emails().isPresent()) {
                for (Email email : usuario.emails().get()) {
                    for (Email emailAtual : usuarioAtual.getEmails()) {
                        if (email.getId().equals(emailAtual.getId())) {
                            emailAtualizador.atualizarEmail(emailAtual, email);
                        }
                    }
                }
            }
            if (usuario.credencial().isPresent()) {
                usuarioAtual.setCredencial(usuario.credencial().get());
            }
            if (usuario.mercadorias().isPresent()) {
                usuarioAtual.getMercadorias().addAll(usuario.mercadorias().get());
            }
            if (usuario.vendas().isPresent()) {
                usuarioAtual.getVendas().addAll(usuario.vendas().get());
            }
            if (usuario.veiculos().isPresent()) {
                usuarioAtual.getVeiculos().addAll(usuario.veiculos().get());
            }
            usuarioRepositorio.save(usuarioAtual);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public void deletarUsuario(Long id, String username) {

        List<Usuario> usuarios = usuarioRepositorio.findAll();
        Usuario usuarioLogado = usuarioSelecionador.selecionarUsuarioNome(usuarios, username);
        Usuario usuario = usuarioRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        boolean permitido = verificadorPermissao.verificar(usuarioLogado.getPerfis(), usuario.getPerfis());

        if (!permitido) {
            throw new IllegalArgumentException("Usuário sem permissão para deletar usuário");
        }

        List<Venda> vendas = vendaRepositorio.findAll();
        for (Venda venda : vendas) {
            if (venda.getCliente() == usuario) {
                venda.setCliente(null);
            }
            if (venda.getFuncionario() == usuario) {
                venda.setFuncionario(null);
            }
            usuario.getVendas().remove(venda);
        }

        List<Veiculo> veiculos = veiculoRepositorio.findAll();
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getProprietario() == usuario) {
                veiculo.setProprietario(null);
            }
        }

        List<Empresa> empresas = repositorioEmpresa.findAll();
        for (Empresa empresa : empresas) {
            empresa.getUsuarios().remove(usuario);
        }

        usuario.getDocumentos().clear();

        usuario.setEndereco(null);

        usuario.getTelefones().clear();

        usuario.getEmails().clear();

        usuario.setCredencial(null);

        usuario.getMercadorias().clear();

        usuarioRepositorio.deleteById(id);
    }
}