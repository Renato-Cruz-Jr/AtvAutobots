package com.autobots.automanager.services;

import com.autobots.automanager.adicionadoresLinks.AdicionadorLinkServico;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.ServicoRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;
import com.autobots.automanager.cadastradores.VendaCadastro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepositorio servicoRepositorio;

    @Autowired
    private VendaCadastro vendaCadastro;

    @Autowired
    private AdicionadorLinkServico adicionadorLinkServico;

    @Autowired
    private VendaRepositorio vendaRepositorio;

    @Autowired
    private RepositorioEmpresa repositorioEmpresa;

    public List<Servico> listarServicos() {
        List<Servico> servicos = servicoRepositorio.findAll();
        adicionadorLinkServico.adicionarLink(servicos);
        return servicos;
    }

    public Servico visualizarServico(Long id) {
        Servico servico = servicoRepositorio.findById(id).orElse(null);
        if (servico != null) {
            adicionadorLinkServico.adicionarLink(servico);
        }
        return servico;
    }

    public List<Servico> visualizarServicosVenda(Long idVenda) {
        Venda venda = vendaRepositorio.findById(idVenda).orElse(null);
        if (venda == null) {
            throw new IllegalArgumentException("Venda não encontrada");
        }
        Set<Servico> servicos = venda.getServicos();
        List<Servico> servicosLista = new ArrayList<>(servicos);
        adicionadorLinkServico.adicionarLink(servicosLista);
        return servicosLista;
    }

    public List<Servico> visualizarServicosEmpresa(Long idEmpresa) {
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        Set<Servico> servicos = empresa.getServicos();
        List<Servico> servicosLista = new ArrayList<>(servicos);
        adicionadorLinkServico.adicionarLink(servicosLista);
        return servicosLista;
    }

    public void cadastrarServico(Servico servico) {
        servicoRepositorio.save(servico);
    }

    public void cadastrarServicoEmpresa(Servico servico, Long idEmpresa) {
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa != null) {
            servicoRepositorio.save(servico);
            empresa.getServicos().add(servico);
            repositorioEmpresa.save(empresa);
        }
        else {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
    }

    public void cadastrarServicoVenda(Servico servico, Long idVenda) {
        Venda venda = vendaRepositorio.findById(idVenda).orElse(null);
        if (venda != null) {
            servicoRepositorio.save(servico);
            venda.getServicos().add(servico);
            vendaRepositorio.save(venda);
        }
        else {
            throw new IllegalArgumentException("Venda não encontrada");
        }
    }

    public void atualizarServico(Long servicoId, Servico servico) {
        Servico servicoAtualizado = servicoRepositorio.findById(servicoId).orElse(null);
        if (servicoAtualizado != null) {
            if (servico.getNome() != null) {
                servicoAtualizado.setNome(servico.getNome());
            }
            if (servico.getDescricao() != null) {
                servicoAtualizado.setDescricao(servico.getDescricao());
            }
            if (servico.getValor() != 0) {
                servicoAtualizado.setValor(servico.getValor());
            }
            servicoRepositorio.save(servicoAtualizado);
        }
        else {
            throw new IllegalArgumentException("Serviço não encontrado");
        }
    }

    public void vincularServicoVenda(Long idServico, Long idVenda) {
        Servico servico = servicoRepositorio.findById(idServico).orElse(null);
        Venda venda = vendaRepositorio.findById(idVenda).orElse(null);
        if (servico != null && venda != null) {
            venda.getServicos().add(servico);
            vendaRepositorio.save(venda);
        }
        else {
            throw new IllegalArgumentException("Serviço ou venda não encontrados");
        }
    }

    public void desvincularServicoVenda(Long idServico, Long idVenda) {
        Servico servico = servicoRepositorio.findById(idServico).orElse(null);
        Venda venda = vendaRepositorio.findById(idVenda).orElse(null);
        if (servico != null && venda != null) {
            venda.getServicos().remove(servico);
            vendaRepositorio.save(venda);
        }
        else {
            throw new IllegalArgumentException("Serviço ou venda não encontrados");
        }
    }

    public void vincularServicoEmpresa(Long idServico, Long idEmpresa) {
        Servico servico = servicoRepositorio.findById(idServico).orElse(null);
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (servico != null && empresa != null) {
            empresa.getServicos().add(servico);
            repositorioEmpresa.save(empresa);
        } else {
            throw new IllegalArgumentException("Serviço ou empresa não encontrados");
        }
    }

    public void desvincularServicoEmpresa(Long idServico, Long idEmpresa) {
        Servico servico = servicoRepositorio.findById(idServico).orElse(null);
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (servico != null && empresa != null) {
            empresa.getServicos().remove(servico);
            repositorioEmpresa.save(empresa);
        } else {
            throw new IllegalArgumentException("Serviço ou empresa não encontrados");
        }
    }

    public void deletarServico(Long id) {
        Servico servico = servicoRepositorio.findById(id).orElse(null);
        if (servico != null) {

            List<Empresa> empresas = repositorioEmpresa.findAll();
            for (Empresa empresa : empresas) {
                empresa.getServicos().remove(servico);
                repositorioEmpresa.save(empresa);
            }

            List<Venda> vendas = vendaRepositorio.findAll();
            for (Venda venda : vendas) {
                venda.getServicos().remove(servico);
                vendaRepositorio.save(venda);
            }

            servicoRepositorio.deleteById(id);
        }
        else {
            throw new IllegalArgumentException("Serviço não encontrado");
        }
    }

}
