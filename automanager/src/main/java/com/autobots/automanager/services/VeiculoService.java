package com.autobots.automanager.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.autobots.automanager.adicionadoresLinks.AdicionadorLinkVeiculo;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;
import com.autobots.automanager.cadastradores.VeiculoCadastro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.autobots.automanager.utilitarios.UsuarioSelecionador;
import com.autobots.automanager.utilitarios.VerificadorPermissao;

@Service
public class VeiculoService {
    @Autowired
    private VeiculoRepositorio veiculoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private VendaRepositorio vendaRepositorio;

    @Autowired
    private VeiculoCadastro veiculoCadastro;

    @Autowired
    private AdicionadorLinkVeiculo adicionadorLinkVeiculo;

    @Autowired
    private UsuarioSelecionador usuarioSelecionador;

    @Autowired
    private VerificadorPermissao verificadorPermissao;

    public List<Veiculo> listarVeiculos() {
        List<Veiculo> veiculos = veiculoRepositorio.findAll();
        adicionadorLinkVeiculo.adicionarLink(veiculos);
        return veiculos;
    }

    public Veiculo visualizarVeiculo(Long id, String usuarioNome) {
        List<Usuario> usuarios =  usuarioRepositorio.findAll();
        Usuario usuarioSelecionado = usuarioSelecionador.selecionarUsuarioNome(usuarios, usuarioNome);
        Veiculo veiculo = veiculoRepositorio.findById(id).orElse(null);
        if (usuarioSelecionado == null) {
            throw new IllegalArgumentException("O usuário não foi encontrado.");
        }
        if (veiculo == null) {
            throw new IllegalArgumentException("O veículo não foi encontrado.");
        }
        if (usuarioSelecionado.getPerfis().contains(PerfilUsuario.ROLE_VENDEDOR)) {
            if (!(veiculo.getProprietario().getPerfis().contains(PerfilUsuario.ROLE_CLIENTE))) {
                throw new IllegalArgumentException("O usuário não tem a permissão para visualizar este veículo.");
            }
        }
        adicionadorLinkVeiculo.adicionarLink(veiculo);
        return veiculo;
    }

    public Veiculo visualizarVeiculo(Long id) {
        Veiculo veiculo = veiculoRepositorio.findById(id).orElse(null);
        if (veiculo == null) {
           throw new IllegalArgumentException("O veículo não foi encontrado.");
        }
        adicionadorLinkVeiculo.adicionarLink(veiculo);
        return veiculo;
    }

    public List<Veiculo> listarVeiculosUsuario(Long idUsuario, String usuarioNome) {
        List<Usuario> usuarios =  usuarioRepositorio.findAll();
        Usuario usuarioSelecionado = usuarioSelecionador.selecionarUsuarioNome(usuarios, usuarioNome);
        Usuario usuario = usuarioRepositorio.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("O usuário não foi encontrado.");
        }
        if (usuarioSelecionado.getPerfis().contains(PerfilUsuario.ROLE_VENDEDOR)) {
            if (!(usuario.getPerfis().contains(PerfilUsuario.ROLE_CLIENTE))) {
                throw new IllegalArgumentException("O usuário não tem permissão para visualizar os veículos deste usuário.");
            }
        }
        Set<Veiculo> veiculos = usuario.getVeiculos();
        List<Veiculo> veiculosLista = new ArrayList<>(veiculos);
        adicionadorLinkVeiculo.adicionarLink(veiculosLista);
        return veiculosLista;
    }

    public ResponseEntity<?> cadastrarVeiculo(Veiculo veiculo) {
        Veiculo veiculoCadastrado = veiculoCadastro.cadastrarVeiculo(veiculo);
        veiculoRepositorio.save(veiculoCadastrado);
        return ResponseEntity.created(null).build();
    }

    public ResponseEntity<?> atualizarVeiculo(Long veiculoId, Veiculo veiculo, String usuarioNome) {
        List<Usuario> usuarios =  usuarioRepositorio.findAll();
        Usuario usuarioSelecionado = usuarioSelecionador.selecionarUsuarioNome(usuarios, usuarioNome);
        if (usuarioSelecionado.getPerfis().contains(PerfilUsuario.ROLE_VENDEDOR)) {
            if (!(veiculo.getProprietario().getPerfis().contains(PerfilUsuario.ROLE_CLIENTE))) {
                throw new IllegalArgumentException("O usuário não tem permissão para atualizar este veículo.");
            }
        }
        Veiculo veiculoAtualizado = veiculoRepositorio.findById(veiculoId).orElse(null);
        if (veiculoAtualizado != null) {
            if (veiculo.getModelo() != null) {
                veiculoAtualizado.setModelo(veiculo.getModelo());
            }
            if (veiculo.getPlaca() != null) {
                veiculoAtualizado.setPlaca(veiculo.getPlaca());
            }
            if (veiculo.getTipo() != null) {
                veiculoAtualizado.setTipo(veiculo.getTipo());
            }
            if (veiculo.getProprietario() != null) {
                veiculoAtualizado.setProprietario(veiculo.getProprietario());
            }
            if (veiculo.getVendas() != null) {
                veiculoAtualizado.setVendas(veiculo.getVendas());
            }
            veiculoRepositorio.save(veiculoAtualizado);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> vincularVeiculoUsuario(Long veiculoId, Long usuarioId, String username) {
        List<Usuario> usuarios =  usuarioRepositorio.findAll();
        Usuario usuarioSelecionado = usuarioSelecionador.selecionarUsuarioNome(usuarios, username);
        Veiculo veiculo = veiculoRepositorio.findById(veiculoId).orElse(null);
        if (veiculo != null) {
            Usuario usuario = usuarioRepositorio.findById(usuarioId).orElse(null);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }
            if (usuarioSelecionado.getPerfis().contains(PerfilUsuario.ROLE_VENDEDOR)) {
                if (!(usuario.getPerfis().contains(PerfilUsuario.ROLE_CLIENTE))) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }
            veiculo.setProprietario(usuario);
            veiculoRepositorio.save(veiculo);
            usuario.getVeiculos().add(veiculo);
            usuarioRepositorio.save(usuario);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> desvincularVeiculoUsuario(Long veiculoId, Long usuarioId, String username) {
        List<Usuario> usuarios =  usuarioRepositorio.findAll();
        Usuario usuarioSelecionado = usuarioSelecionador.selecionarUsuarioNome(usuarios, username);
        Veiculo veiculo = veiculoRepositorio.findById(veiculoId).orElse(null);
        if (veiculo != null) {
            Usuario usuario = usuarioRepositorio.findById(usuarioId).orElse(null);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }
            if (usuarioSelecionado.getPerfis().contains(PerfilUsuario.ROLE_VENDEDOR)) {
                if (!(usuario.getPerfis().contains(PerfilUsuario.ROLE_CLIENTE))) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }
            veiculo.setProprietario(null);
            veiculoRepositorio.save(veiculo);
            usuario.getVeiculos().remove(veiculo);
            usuarioRepositorio.save(usuario);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> excluirVeiculo(Long id) {
        Veiculo veiculo = veiculoRepositorio.findById(id).orElse(null);
        if (veiculo != null) {
            veiculo.getProprietario().getVeiculos().remove(veiculo);
            usuarioRepositorio.save(veiculo.getProprietario());
            for (Venda venda : veiculo.getVendas()) {
                venda.setVeiculo(null);
                vendaRepositorio.save(venda);
            }
            veiculoRepositorio.delete(veiculo);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}