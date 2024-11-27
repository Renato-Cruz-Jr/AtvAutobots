package com.autobots.automanager.repositorios;

import com.autobots.automanager.entidades.Credencial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CredencialRepositorio extends JpaRepository<Credencial, Long> {
    @Query("SELECT c FROM Credencial c WHERE c.inativo = false")
    List<Credencial> findActiveCredenciais();

    @Query("SELECT c FROM Credencial c WHERE c.id = :id AND c.inativo = false")
    Optional<Credencial> findActiveCredencialById(@Param("id") Long id);
}
