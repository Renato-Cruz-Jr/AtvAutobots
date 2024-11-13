package com.autobots.automanager.modelo;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DocumentoCadastrador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    public void cadastrar(Cliente cliente, Documento documento) {
        if (documento != null) {
            if (!verificador.verificar(documento.getTipo()) && !verificador.verificar(documento.getNumero())) {
                Documento documentoCadastrado = new Documento();
                documentoCadastrado.setTipo(documento.getTipo());
                documentoCadastrado.setNumero(documento.getNumero());
                cliente.getDocumentos().add(documentoCadastrado);
            }
        }
    }

    public void cadastrar(Cliente cliente, List<Documento> documentos) {
        for (Documento documento : documentos) {
            cadastrar(cliente, documento);
        }
    }
}