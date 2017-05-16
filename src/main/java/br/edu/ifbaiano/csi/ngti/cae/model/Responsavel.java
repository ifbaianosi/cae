package br.edu.ifbaiano.csi.ngti.cae.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="responsavel")
public class Responsavel extends Entidade{

	private static final long serialVersionUID = 3236244419255066561L;

	@NotBlank(message="O nome é obrigatorio")
	private String nome;
	
	@NotBlank(message="O número para contato é obrigatório")
	@Size(max=20, message="O número para contato deve conter no máximo {max} caracteres")
	private String contato;
	
	private Boolean whatsapp;
	
	@NotNull(message="Selecione o grau de parentesco")
	@Enumerated(EnumType.STRING)
	@Transient
	private GrauParentesco parentesco;
	
	@Transient
	private String uuid;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public Boolean getWhatsapp() {
		return whatsapp;
	}

	public void setWhatsapp(Boolean whatsapp) {
		this.whatsapp = whatsapp;
	}

	public GrauParentesco getParentesco() {
		return parentesco;
	}

	public void setParentesco(GrauParentesco parentesco) {
		this.parentesco = parentesco;
	}
	
	public String getUuid() {
		return uuid;
	}
}
