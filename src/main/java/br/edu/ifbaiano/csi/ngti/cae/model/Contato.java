package br.edu.ifbaiano.csi.ngti.cae.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="contato")
@DynamicUpdate
public class Contato extends Entidade{

	private static final long serialVersionUID = -4837232815834243558L;
	
	@NotBlank(message="O número para contato é obrigatório")
	@Size(max=20, message="O número para contato deve conter no máximo {max} caracteres")
	private String numero;
	
	private Boolean whatsapp;
	
	@Size(max=20, message="O número do whatsapp deve conter no máximo {max} caracteres")
	private String numero_whatsapp;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Boolean getWhatsapp() {
		return whatsapp;
	}

	public void setWhatsapp(Boolean whatsapp) {
		this.whatsapp = whatsapp;
	}

	public String getNumero_whatsapp() {
		return numero_whatsapp;
	}

	public void setNumero_whatsapp(String numero_whatsapp) {
		this.numero_whatsapp = numero_whatsapp;
	}

	
}
