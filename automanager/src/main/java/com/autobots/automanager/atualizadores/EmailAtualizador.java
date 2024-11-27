package com.autobots.automanager.atualizadores;

import com.autobots.automanager.entidades.Email;
import org.springframework.stereotype.Component;

@Component
public class EmailAtualizador {
    public Email atualizarEmail(Email email, Email novoEmail) {
        email.setEndereco(novoEmail.getEndereco());
        return email;
    }
}