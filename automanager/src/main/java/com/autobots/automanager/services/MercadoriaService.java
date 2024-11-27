package com.autobots.automanager.services;

import com.autobots.automanager.adicionadoresLinks.AdicionadorLinkMercadoria;
import com.autobots.automanager.controles.dto.CadastradorMercadoriaDto;
import com.autobots.automanager.entidades.*;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;
import com.autobots.automanager.cadastradores.MercadoriaCadastro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MercadoriaService {

    @Autowired
    private MercadoriaRepositorio mercadoriaRepositorio;

    @Autowired
    private AdicionadorLinkMercadoria adicionadorLinkMercadoria;

    @Autowired
    private RepositorioEmpresa repositorioEmpresa;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private VendaRepositorio vendaRepositorio;

    @Autowired
    private MercadoriaCadastro mercadoriaCadastro;

    public List<Mercadoria> listarMercadorias() {
        List<Mercadoria> mercadorias = mercadoriaRepositorio.findAll();
        adicionadorLinkMercadoria.adicionarLink(mercadorias);
        return mercadorias;
    }

    public Mercadoria visualizarMercadoria(Long id) {
        Mercadoria mercadoria = mercadoriaRepositorio.findById(id).orElse(null);
        if (mercadoria != null) {
            adicionadorLinkMercadoria.adicionarLink(mercadoria);
        }
        return mercadoria;
    }

    public List<Mercadoria> visualizarMercadoriaEmpresa(Long idEmpresa) {
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        Set<Mercadoria> mercadorias = empresa.getMercadorias();
        List<Mercadoria> mercadoriasLista = new ArrayList<>(mercadorias);
        adicionadorLinkMercadoria.adicionarLink(mercadoriasLista);
        return mercadoriasLista;
    }

    public List<Mercadoria> visualizarMercadoriaUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepositorio.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario não encontrado");
        }
        Set<Mercadoria> mercadorias = usuario.getMercadorias();
        List<Mercadoria> mercadoriasLista = new ArrayList<>(mercadorias);
        adicionadorLinkMercadoria.adicionarLink(mercadoriasLista);
        return mercadoriasLista;
    }

    public void cadastrarMercadoria(CadastradorMercadoriaDto mercadoria) {
        Mercadoria mercadoriaCadastrada = mercadoriaCadastro.cadastrarMercadoria(mercadoria);
        mercadoriaRepositorio.save(mercadoriaCadastrada);
    }

    public void cadastrarMercadoriaEmpresa(CadastradorMercadoriaDto mercadoria, Long idEmpresa) {
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        Mercadoria mercadoriaCadastrada = mercadoriaCadastro.cadastrarMercadoria(mercadoria);
        mercadoriaRepositorio.save(mercadoriaCadastrada);
        empresa.getMercadorias().add(mercadoriaCadastrada);
        repositorioEmpresa.save(empresa);
    }

    public void cadastrarMercadoriaUsuario(CadastradorMercadoriaDto mercadoria, Long idUsuario) {
        Usuario usuario  = usuarioRepositorio.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario não encontrado");
        }
        Mercadoria mercadoriaCadastrada = mercadoriaCadastro.cadastrarMercadoria(mercadoria);
        mercadoriaRepositorio.save(mercadoriaCadastrada);
        usuario.getMercadorias().add(mercadoriaCadastrada);
        usuarioRepositorio.save(usuario);
    }

    public void atualizarMercadoria(Long id, Mercadoria mercadoria) {
        Mercadoria mercadoriaAtual = mercadoriaRepositorio.findById(id).orElse(null);
        if (mercadoriaAtual != null) {
            if (mercadoria.getValidade() != null) {
                mercadoriaAtual.setValidade(mercadoria.getValidade());
            }
            if (mercadoria.getFabricao() != null) {
                mercadoriaAtual.setFabricao(mercadoria.getFabricao());
            }
            if (mercadoria.getCadastro() != null) {
                mercadoriaAtual.setCadastro(mercadoria.getCadastro());
            }
            if (mercadoria.getNome() != null) {
                mercadoriaAtual.setNome(mercadoria.getNome());
            }
            if (mercadoria.getQuantidade() != 0) {
                mercadoriaAtual.setQuantidade(mercadoria.getQuantidade());
            }
            if (mercadoria.getValor() != 0) {
                mercadoriaAtual.setValor(mercadoria.getValor());
            }
            if (mercadoria.getDescricao() != null) {
                mercadoriaAtual.setDescricao(mercadoria.getDescricao());
            }
        }

        mercadoriaRepositorio.save(mercadoriaAtual);
    }

    public void vincularMercadoriaEmpresa(Long idMercadoria, Long idEmpresa) {
        Mercadoria mercadoria = mercadoriaRepositorio.findById(idMercadoria).orElse(null);
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (mercadoria == null) {
            throw new IllegalArgumentException("Mercadoria não encontrada");
        }
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        empresa.getMercadorias().add(mercadoria);
        repositorioEmpresa.save(empresa);
    }

    public void vincularMercadoriaUsuario(Long idMercadoria, Long idUsuario) {
        Mercadoria mercadoria = mercadoriaRepositorio.findById(idMercadoria).orElse(null);
        Usuario usuario = usuarioRepositorio.findById(idUsuario).orElse(null);
        if (mercadoria == null) {
            throw new IllegalArgumentException("Mercadoria não encontrada");
        }
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario não encontrado");
        }
        usuario.getMercadorias().add(mercadoria);
        usuarioRepositorio.save(usuario);
    }

    public void desvincularMercadoriaEmpresa(Long idMercadoria, Long idEmpresa) {
        Mercadoria mercadoria = mercadoriaRepositorio.findById(idMercadoria).orElse(null);
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (mercadoria == null) {
            throw new IllegalArgumentException("Mercadoria não encontrada");
        }
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        empresa.getMercadorias().remove(mercadoria);
        repositorioEmpresa.save(empresa);
    }

    public void desvincularMercadoriaUsuario(Long idMercadoria, Long idUsuario) {
        Mercadoria mercadoria = mercadoriaRepositorio.findById(idMercadoria).orElse(null);
        Usuario usuario = usuarioRepositorio.findById(idUsuario).orElse(null);
        if (mercadoria == null) {
            throw new IllegalArgumentException("Mercadoria não encontrada");
        }
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario não encontrado");
        }
        usuario.getMercadorias().remove(mercadoria);
        usuarioRepositorio.save(usuario);
    }

    public void deletarMercadoria(Long id) {
        Mercadoria mercadoria = mercadoriaRepositorio.findById(id).orElse(null);
        if (mercadoria == null) {
            throw new IllegalArgumentException("Mercadoria não encontrada");
        }

        List<Usuario> usuarios = usuarioRepositorio.findAll();
        for (Usuario usuario : usuarios) {
            usuario.getMercadorias().remove(mercadoria);
            usuarioRepositorio.save(usuario);
        }

        List<Empresa> empresas = repositorioEmpresa.findAll();
        for (Empresa empresa : empresas) {
            empresa.getMercadorias().remove(mercadoria);
            repositorioEmpresa.save(empresa);
        }

        List<Venda> vendas = vendaRepositorio.findAll();
        for (Venda venda : vendas) {
            venda.getMercadorias().remove(mercadoria);
            vendaRepositorio.save(venda);
        }

        mercadoriaRepositorio.deleteById(id);

    }
}
