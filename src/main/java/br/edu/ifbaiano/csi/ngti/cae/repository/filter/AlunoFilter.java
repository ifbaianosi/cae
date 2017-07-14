package br.edu.ifbaiano.csi.ngti.cae.repository.filter;

import java.time.LocalDate;

import br.edu.ifbaiano.csi.ngti.cae.model.Alojamento;
import br.edu.ifbaiano.csi.ngti.cae.model.Curso;
import br.edu.ifbaiano.csi.ngti.cae.model.Regime;
import br.edu.ifbaiano.csi.ngti.cae.model.SerieTurma;
import br.edu.ifbaiano.csi.ngti.cae.model.Sexo;
import br.edu.ifbaiano.csi.ngti.cae.model.Status;

public class AlunoFilter {

	private String matricula;
	private String nome;
	private LocalDate dataNascimento;
	private Sexo sexo;
	private Regime regime;
	private Curso curso;
	private SerieTurma serieTurma;
	private Alojamento alojamento;
	private Integer apartamento;
	private String nomeSocial;
	private Status status;
	private Boolean saida;
	
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
	public Regime getRegime() {
		return regime;
	}
	public void setRegime(Regime regime) {
		this.regime = regime;
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
	public Integer getApartamento() {
		return apartamento;
	}
	public void setApartamento(Integer apartamento) {
		this.apartamento = apartamento;
	}
	public Alojamento getAlojamento() {
		return alojamento;
	}
	public void setAlojamento(Alojamento alojamento) {
		this.alojamento = alojamento;
	}
	public String getNomeSocial() {
		return nomeSocial;
	}
	public void setNomeSocial(String nomeSocial) {
		this.nomeSocial = nomeSocial;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Boolean getSaida() {
		return saida;
	}
	public void setSaida(Boolean saida) {
		this.saida = saida;
	}
	
}
