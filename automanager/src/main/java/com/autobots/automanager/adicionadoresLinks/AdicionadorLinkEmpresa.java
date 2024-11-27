package com.autobots.automanager.adicionadoresLinks;

import com.autobots.automanager.controles.EmpresaControle;
import com.autobots.automanager.entidades.Empresa;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AdicionadorLinkEmpresa implements AdicionadorLink<Empresa> {

    @Override
    public void adicionarLink(List<Empresa> lista) {
        for (Empresa empresa : lista) {
            long id = empresa.getId();
            Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmpresaControle.class).visualizarEmpresa(id)).withSelfRel();
            empresa.add(linkProprio);
        }
    }

    @Override
    public void adicionarLink(Empresa objeto) {
        Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmpresaControle.class).listarEmpresas()).withRel("empresas");
        objeto.add(linkProprio);
    }
}