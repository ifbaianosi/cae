package br.edu.ifbaiano.csi.ngti.cae.dto;

import br.edu.ifbaiano.csi.ngti.cae.model.Curso;
import br.edu.ifbaiano.csi.ngti.cae.model.SerieTurma;
import br.edu.ifbaiano.csi.ngti.cae.model.Sexo;

public class AlunoDTO {

	private Long codigo;
	private String nome;
	private String matricula;
	private Curso curso;
	private String serieTurma;
	private String sexo;
	
	
	
	public AlunoDTO() {}

	public AlunoDTO(Long codigo, String nome, String matricula, SerieTurma serieTurma, Sexo sexo) {
		this.codigo = codigo;
		this.nome = nome;
		this.matricula = matricula;
		this.serieTurma = serieTurma.getDescricao();
		this.sexo = sexo.getDescricao();
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public String getSerieTurma() {
		return serieTurma;
	}

	public void setSerieTurma(String serieTurma) {
		this.serieTurma = serieTurma;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	
}
