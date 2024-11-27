package com.autobots.automanager.entidades;

import org.springframework.hateoas.RepresentationModel;
import com.autobots.automanager.enumeracoes.TipoVeiculo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = { "proprietario", "vendas" })
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
	@JsonIgnoreProperties(value = {"veiculos", "endereco", "mercadorias", "vendas"})
	private Usuario proprietario;
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JsonIgnoreProperties(value = {"veiculo", "endereco", "mercadorias", "vendas"})
	private Set<Venda> vendas = new HashSet<>();
}