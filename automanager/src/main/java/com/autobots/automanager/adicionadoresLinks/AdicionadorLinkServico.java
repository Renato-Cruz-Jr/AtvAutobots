package com.autobots.automanager.adicionadoresLinks;

import com.autobots.automanager.controles.ServicoControle;
import com.autobots.automanager.entidades.Servico;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AdicionadorLinkServico implements AdicionadorLink<Servico> {

        @Override
        public void adicionarLink(List<Servico> lista) {
            for (Servico servico : lista) {
                long id = servico.getId();
                Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ServicoControle.class).visualizarServico(id)).withSelfRel();
                servico.add(linkProprio);
            }
        }

        @Override
        public void adicionarLink(Servico objeto) {
            Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ServicoControle.class).listarServicos()).withRel("servicos");
            objeto.add(linkProprio);
        }
}