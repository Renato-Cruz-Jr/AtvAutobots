package com.autobots.automanager.entidades;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import javax.persistence.*;
import org.springframework.hateoas.RepresentationModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@EqualsAndHashCode(exclude = { "cliente", "funcionario", "veiculo" }, callSuper=false)
@Entity
public class Venda extends RepresentationModel<Venda>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Date cadastro;
	@Column(nullable = false, unique = true)
	private String identificacao;
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JsonIgnoreProperties(value = { "credenciais", "mercadorias", "vendas", "veiculos" })
	private Usuario cliente;
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JsonIgnoreProperties(value = { "credenciais", "mercadorias", "vendas", "veiculos" })
	private Usuario funcionario;
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	private Set<Mercadoria> mercadorias = new HashSet<>();
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	private Set<Servico> servicos = new HashSet<>();
	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JsonIgnoreProperties(value = { "proprietario" , "vendas" })
	private Veiculo veiculo;
}