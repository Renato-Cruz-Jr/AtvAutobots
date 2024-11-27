package com.autobots.automanager.services;

import com.autobots.automanager.adicionadoresLinks.AdicionadorLinkVeiculo;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;
import com.autobots.automanager.cadastradores.VeiculoCadastro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public List<Veiculo> listarVeiculos() {
        List<Veiculo> veiculos = veiculoRepositorio.findAll();
        adicionadorLinkVeiculo.adicionarLink(veiculos);
        return veiculos;
    }

    public Veiculo visualizarVeiculo(Long id) {
        Veiculo veiculo = veiculoRepositorio.findById(id).orElse(null);
        if (veiculo != null) {
            adicionadorLinkVeiculo.adicionarLink(veiculo);
        }
        return veiculo;
    }

    public List<Veiculo> listarVeiculosUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepositorio.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
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

    public ResponseEntity<?> atualizarVeiculo(Long veiculoId, Veiculo veiculo) {
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

    public ResponseEntity<?> vincularVeiculoUsuario(Long veiculoId, Long usuarioId) {
        Veiculo veiculo = veiculoRepositorio.findById(veiculoId).orElse(null);
        if (veiculo != null) {
            Usuario usuario = usuarioRepositorio.findById(usuarioId).orElse(null);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
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

    public ResponseEntity<?> desvincularVeiculoUsuario(Long veiculoId, Long usuarioId) {
        Veiculo veiculo = veiculoRepositorio.findById(veiculoId).orElse(null);
        if (veiculo != null) {
            Usuario usuario = usuarioRepositorio.findById(usuarioId).orElse(null);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
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
