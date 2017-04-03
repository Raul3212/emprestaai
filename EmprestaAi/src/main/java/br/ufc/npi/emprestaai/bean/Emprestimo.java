/*package br.ufc.npi.emprestaai.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Emprestimo {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="contato_id")
	private Contato contato;
	
	@OneToOne
	@JoinColumn(name="item_id")
	private Item item;
	
	private Date data;
	
	@Column(name="contato_id", insertable=false, updatable=false)
	private Long contatoId;
	
	@Column(name="item_id", insertable=false, updatable=false)
	private Long itemId;
	
	public Contato getContato() {
		return contato;
	}
	public void setContato(Contato contato) {
		this.contato = contato;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
}
*/