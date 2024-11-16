package com.autobots.automanager.modelo;

import java.util.List;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Cliente;
import org.springframework.stereotype.Component;

@Component
public class DocumentoSelecionador {
    public Documento selecionar(List<Documento> documentos, long id) {
        Documento selecionado = null;
        for (Documento documento : documentos) {
            if (documento.getId() == id) {
                selecionado = documento;
            }
        }
        return selecionado;
    }
}