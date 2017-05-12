package br.edu.ifbaiano.csi.ngti.cae.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="responsavel")
public class Responsavel extends Entidade{

	private static final long serialVersionUID = 3236244419255066561L;

	@NotBlank(message="O nome é obrigatorio")
	private String nome;
	
	@ManyToOne
	@JoinColumn(name="codigo_contato")
	private Contato contato;
	
	@Transient
	private GrauParentesco parentesco;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public GrauParentesco getParentesco() {
		return parentesco;
	}

	public void setParentesco(GrauParentesco parentesco) {
		this.parentesco = parentesco;
	}
	
	
}
