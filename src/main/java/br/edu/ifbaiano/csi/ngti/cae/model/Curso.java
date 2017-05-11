package br.edu.ifbaiano.csi.ngti.cae.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="curso")
public class Curso extends Entidade{

	private static final long serialVersionUID = -6607842131219736997L;
	
	@NotBlank(message="O nome é obrigatório")
	@Size(max=80, message="O nome deve conter no máximo {max} caracteres")
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	} 

	
}
