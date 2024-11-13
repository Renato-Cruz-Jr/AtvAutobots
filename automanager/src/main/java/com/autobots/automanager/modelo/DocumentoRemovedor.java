package com.autobots.automanager.modelo;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DocumentoRemovedor {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    public void excluir(Cliente cliente, Documento documento) {
        if (documento != null) {
            if (!verificador.verificar(documento.getTipo()) && !verificador.verificar(documento.getNumero())) {
                cliente.getDocumentos().remove(documento);
            }
        }
    }

    public void excluir(Cliente cliente, List<Documento> documentos) {
        for (Documento documentoExcluido : documentos) {
            if (documentoExcluido != null) {
                excluir(cliente, documentoExcluido);
            }
        }
    }
}