package com.autobots.automanager.services;

import com.autobots.automanager.adicionadoresLinks.AdicionadorLinkVenda;
import com.autobots.automanager.controles.dto.AtualizadorVendaDto;
import com.autobots.automanager.entidades.*;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;
import com.autobots.automanager.atualizadores.MercadoriaAtualizador;
import com.autobots.automanager.atualizadores.ServicoAtualizador;
import com.autobots.automanager.cadastradores.VendaCadastro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class VendaService {

    @Autowired
    private VendaRepositorio vendaRepositorio;

    @Autowired
    private RepositorioEmpresa repositorioEmpresa;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private VeiculoRepositorio veiculoRepositorio;

    @Autowired
    private VendaCadastro vendaCadastro;

    @Autowired
    private AdicionadorLinkVenda adicionadorLinkVenda;

    @Autowired
    private MercadoriaAtualizador mercadoriaAtualizador;

    @Autowired
    private ServicoAtualizador servicoAtualizador;

    public List<Venda> listarVendas() {
        List<Venda> vendas = vendaRepositorio.findAll();
        adicionadorLinkVenda.adicionarLink(vendas);
        return vendas;
    }

    public Venda visualizarVenda(Long id) {
        Venda venda = vendaRepositorio.findById(id).orElse(null);
        if (venda != null) {
            adicionadorLinkVenda.adicionarLink(venda);
        }
        return venda;
    }

    public List<Venda> visualizarVendasEmpresa(Long idEmpresa) {
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        Set<Venda> vendas = empresa.getVendas();
        List<Venda> vendasLista = new ArrayList<>(vendas);
        adicionadorLinkVenda.adicionarLink(vendasLista);
        return vendasLista;
    }

    public List<Venda> visualizarVendasUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepositorio.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        Set<Venda> vendas = usuario.getVendas();
        List<Venda> vendasLista = new ArrayList<>(vendas);
        adicionadorLinkVenda.adicionarLink(vendasLista);
        return vendasLista;
    }

    public void cadastrarVenda(Venda venda) {
        Venda vendaCadastrada = vendaCadastro.cadastrarVenda(venda);
        vendaRepositorio.save(vendaCadastrada);
    }

    public void cadastrarVendaEmpresa(Long idEmpresa, Venda venda) {
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        Venda vendaCadastrada = vendaCadastro.cadastrarVenda(venda);
        empresa.getVendas().add(vendaCadastrada);
        repositorioEmpresa.save(empresa);
    }

    public void cadastrarVendaUsuario(Long idUsuario, Venda venda, String tipoUsuario) {
        Usuario usuario = usuarioRepositorio.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        Venda vendaCadastrada = vendaCadastro.cadastrarVenda(venda);
        if (tipoUsuario.equalsIgnoreCase("cliente")) {
            vendaCadastrada.setCliente(usuario);
        } else if (tipoUsuario.equalsIgnoreCase("funcionario")) {
            vendaCadastrada.setFuncionario(usuario);
        }
        vendaRepositorio.save(vendaCadastrada);
        usuario.getVendas().add(vendaCadastrada);
        usuarioRepositorio.save(usuario);
    }

    public void atualizarVenda(Long vendaId, AtualizadorVendaDto venda) {
        Venda vendaAtualizada = vendaRepositorio.findById(vendaId).orElse(null);
        if (vendaAtualizada != null) {
            if (venda.identificacao().isPresent()) {
                vendaAtualizada.setIdentificacao(venda.identificacao().get());
            }
            if (venda.cliente().isPresent()) {
                vendaAtualizada.setCliente(venda.cliente().get());
            }
            if (venda.funcionario().isPresent()) {
                vendaAtualizada.setFuncionario(venda.funcionario().get());
            }
            if (venda.mercadorias().isPresent()) {
                for (Mercadoria mercadoria : venda.mercadorias().get()) {
                    for (Mercadoria mercadoriaAtual : vendaAtualizada.getMercadorias()) {
                        if (mercadoriaAtual.getId().equals(mercadoria.getId())) {
                            mercadoriaAtualizador.atualizarMercadoria(mercadoriaAtual, mercadoria);
                        }
                    }
                }
            }
            if (venda.servicos().isPresent()) {
                for (Servico servico : venda.servicos().get()) {
                    for (Servico servicoAtual : vendaAtualizada.getServicos()) {
                        if (servicoAtual.getId().equals(servico.getId())) {
                            servicoAtualizador.atualizarServico(servicoAtual, servico);
                        }
                    }
                }
            }
            if (venda.veiculo().isPresent()) {
                vendaAtualizada.setVeiculo(venda.veiculo().get());
            }

            vendaRepositorio.save(vendaAtualizada);
        }
    }

    public void vincularVendaUsuario(Long idVenda, Long idUsuario, String tipoUsuario) {
        Venda venda = vendaRepositorio.findById(idVenda).orElse(null);
        Usuario usuario = usuarioRepositorio.findById(idUsuario).orElse(null);
        if (venda == null) {
            throw new IllegalArgumentException("Venda não encontrada");
        }
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        if (tipoUsuario.equalsIgnoreCase("cliente")) {
            venda.setCliente(usuario);
        } else if (tipoUsuario.equalsIgnoreCase("funcionario")) {
            venda.setFuncionario(usuario);
        }
        vendaRepositorio.save(venda);
        usuario.getVendas().add(venda);
        usuarioRepositorio.save(usuario);
    }

    public void vincularVendaEmpresa(Long idVenda, Long idEmpresa) {
        Venda venda = vendaRepositorio.findById(idVenda).orElse(null);
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (venda == null) {
            throw new IllegalArgumentException("Venda não encontrada");
        }
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        empresa.getVendas().add(venda);
        repositorioEmpresa.save(empresa);
    }

    public void desvincularVendaUsuario(Long idVenda, Long idUsuario, String tipoUsuario) {
        Venda venda = vendaRepositorio.findById(idVenda).orElse(null);
        Usuario usuario = usuarioRepositorio.findById(idUsuario).orElse(null);
        if (venda == null) {
            throw new IllegalArgumentException("Venda não encontrada");
        }
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        if (tipoUsuario.equalsIgnoreCase("cliente")) {
            venda.setCliente(null);
        } else if (tipoUsuario.equalsIgnoreCase("funcionario")) {
            venda.setFuncionario(null);
        }
        vendaRepositorio.save(venda);
        usuario.getVendas().remove(venda);
        usuarioRepositorio.save(usuario);
    }

    public void desvincularVendaEmpresa(Long idVenda, Long idEmpresa) {
        Venda venda = vendaRepositorio.findById(idVenda).orElse(null);
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (venda == null) {
            throw new IllegalArgumentException("Venda não encontrada");
        }
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        empresa.getVendas().remove(venda);
        repositorioEmpresa.save(empresa);
    }

    public void deletarVenda(Long id) {
        Venda venda = vendaRepositorio.findById(id).orElse(null);
        if (venda == null) {
            throw new IllegalArgumentException("Venda não encontrada");
        }

        List<Usuario> usuarios = usuarioRepositorio.findAll();
        for (Usuario usuario : usuarios) {
            usuario.getVendas().remove(venda);
            usuarioRepositorio.save(usuario);
        }

        List<Empresa> empresas = repositorioEmpresa.findAll();
        for (Empresa empresa : empresas) {
            empresa.getVendas().remove(venda);
            repositorioEmpresa.save(empresa);
        }

        Veiculo veiculo = venda.getVeiculo();
        veiculo.getVendas().remove(venda);
        vendaRepositorio.save(venda);

        vendaRepositorio.delete(venda);
    }
}
