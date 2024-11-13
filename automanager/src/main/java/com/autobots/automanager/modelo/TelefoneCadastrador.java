package com.autobots.automanager.modelo;

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Cliente;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class TelefoneCadastrador {
    private final StringVerificadorNulo verificador = new StringVerificadorNulo();

    public void cadastrar(Cliente cliente, Telefone telefone) {
        if (telefone != null) {
            if (!verificador.verificar(telefone.getDdd()) && !verificador.verificar(telefone.getNumero())) {
                Telefone telefoneCadastrado = new Telefone();
                telefoneCadastrado.setDdd(telefone.getDdd());
                telefoneCadastrado.setNumero(telefone.getNumero());
                cliente.getTelefones().add(telefoneCadastrado);
            }
        }
    }

    public void cadastrar(Cliente cliente, List<Telefone> telefones) {
        for (Telefone telefone : telefones) {
            cadastrar(cliente, telefone);
        }
    }
}
