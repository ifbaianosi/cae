package br.edu.ifbaiano.csi.ngti.cae.repository.filter;

import java.time.LocalDate;

import br.edu.ifbaiano.csi.ngti.cae.model.Curso;
import br.edu.ifbaiano.csi.ngti.cae.model.Identificacao;
import br.edu.ifbaiano.csi.ngti.cae.model.SerieTurma;
import br.edu.ifbaiano.csi.ngti.cae.model.Sexo;

public class AlunoFilter {

	private String matricula;
	private String nome;
	private LocalDate dataNascimento;
	private Sexo sexo;
	private Identificacao identificacao;
	private Curso curso;
	private SerieTurma serieTurma;
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public Sexo getSexo() {
		return sexo;
	}
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	public Identificacao getIdentificacao() {
		return identificacao;
	}
	public void setIdentificacao(Identificacao identificacao) {
		this.identificacao = identificacao;
	}
	public Curso getCurso() {
		return curso;
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	public SerieTurma getSerieTurma() {
		return serieTurma;
	}
	public void setSerieTurma(SerieTurma serieTurma) {
		this.serieTurma = serieTurma;
	}
	
}
