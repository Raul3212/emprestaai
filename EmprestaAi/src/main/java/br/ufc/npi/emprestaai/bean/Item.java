package br.ufc.npi.emprestaai.bean;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
		
	private String nome;
	
	private String descricao;
	
	@OneToOne
	@JoinColumn(name="contato_id")
	private Contato contato;
	
	@OneToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	@Column(insertable=false,updatable=false)
	private Long contato_id;
	
	@Column(nullable=true, columnDefinition="DATE DEFAULT NULL")
	private Date dataDevolucao;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato usuario) {
		this.contato = usuario;
	}
		
	public boolean equals(Item obj) {
		return this.id == obj.id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Long getContato_id() {
		return contato_id;
	}

	public void setContato_id(Long contato_id) {
		this.contato_id = contato_id;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}
	
}
