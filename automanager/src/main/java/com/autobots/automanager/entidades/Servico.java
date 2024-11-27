package com.autobots.automanager.entidades;

import javax.persistence.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
@Entity
public class Servico extends RepresentationModel<Servico> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private double valor;
	@Column
	private String descricao;
}