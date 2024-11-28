package com.autobots.automanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@Service
public class AutenticacaoService {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(Credencial credencial) {
        Usuario usuario = new Usuario();
        usuario.setNome(credencial.getNomeUsuario());
        credencial.setSenha(passwordEncoder.encode(credencial.getSenha()));
        usuario.setCredencial(credencial);
        return usuarioRepositorio.save(usuario);
    }
}