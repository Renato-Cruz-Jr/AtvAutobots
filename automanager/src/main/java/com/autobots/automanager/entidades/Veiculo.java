package com.autobots.automanager.entidades;

import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.hateoas.RepresentationModel;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import com.autobots.automanager.enumeracoes.TipoVeiculo;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@EqualsAndHashCode(exclude = { "proprietario", "vendas" }, callSuper = false)
@Entity
public class Veiculo extends RepresentationModel<Veiculo> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private TipoVeiculo tipo;
	@Column(nullable = false)
	private String modelo;
	@Column(nullable = false)
	private String placa;
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JsonManagedReference
	private Usuario proprietario;
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JsonManagedReference
	private Set<Venda> vendas = new HashSet<>();
}