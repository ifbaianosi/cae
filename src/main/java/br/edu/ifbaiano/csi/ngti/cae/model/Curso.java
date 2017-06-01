package br.edu.ifbaiano.csi.ngti.cae.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="curso")
public class Curso extends Entidade{

	private static final long serialVersionUID = -6607842131219736997L;
	
	@NotBlank(message="O nome é obrigatório")
	@Size(max=80, message="O nome deve conter no máximo {max} caracteres")
	private String nome;
	
	@NotBlank(message="A sigla é obrigatória")
	@Size(max=80, message="A sigla deve conter no máximo {max} caracteres")
	private String sigla;
	

	@NotNull(message="Selecione o tipo do curso")
	@Enumerated(EnumType.STRING)
	@Column(name="tipo_curso")
	private TipoCurso tipoCurso;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public TipoCurso getTipoCurso() {
		return tipoCurso;
	}

	public void setTipoCurso(TipoCurso tipoCurso) {
		this.tipoCurso = tipoCurso;
	} 

	
}
