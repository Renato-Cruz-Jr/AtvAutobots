package com.autobots.automanager.repositorios;

import com.autobots.automanager.entidades.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VendaRepositorio extends JpaRepository<Venda, Long> {
    List<Venda> findByClienteId(long clienteId);
    List<Venda> findByFuncionarioId(Long funcionarioId);
}