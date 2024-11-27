package com.autobots.automanager.atualizadores;

import com.autobots.automanager.entidades.Documento;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class DocumentoAtualizador {
    public Documento atualizarDocumento(Documento documento, Documento novoDocumento) {
        documento.setTipo(novoDocumento.getTipo());
        documento.setDataEmissao(new Date());
        documento.setNumero(novoDocumento.getNumero());
        return documento;
    }
}