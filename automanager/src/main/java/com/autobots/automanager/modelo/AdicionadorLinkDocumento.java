package com.autobots.automanager.modelo;

import org.springframework.stereotype.Component;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import com.autobots.automanager.controles.ClienteControle;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.controles.DocumentoControle;

import java.util.List;

@Component
public class AdicionadorLinkDocumento implements AdicionadorLink<Documento> {
    @Override
    public void adicionarLink(List<Documento> lista) {
        for (Documento documento : lista) {
            long id = documento.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(DocumentoControle.class)
                            .visualizarDocumento(id))
                    .withSelfRel();
            documento.add(linkProprio);
        }
    }

    @Override
    public void adicionarLink(Documento objeto) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .listarDocumentos())
                .withRel("documentos");
        objeto.add(linkProprio);
    }
}
