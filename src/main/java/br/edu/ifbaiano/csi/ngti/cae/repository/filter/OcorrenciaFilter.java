package br.edu.ifbaiano.csi.ngti.cae.repository.filter;

import java.time.LocalDate;

import br.edu.ifbaiano.csi.ngti.cae.model.Alojamento;
import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.model.Curso;
import br.edu.ifbaiano.csi.ngti.cae.model.SerieTurma;
import br.edu.ifbaiano.csi.ngti.cae.model.Usuario;

public class OcorrenciaFilter {

	private Long numero;
	private LocalDate dataOcorrido;
	private LocalDate dataOcorridoAte;
	private String local;
	private Aluno aluno;
	private Usuario usuario;
	private Curso curso;
	private SerieTurma serieTurma;
	private Alojamento alojamento;
	private Integer apartamento;
	
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	public LocalDate getDataOcorrido() {
		return dataOcorrido;
	}
	public LocalDate getDataOcorridoAte() {
		return dataOcorridoAte;
	}
	public void setDataOcorridoAte(LocalDate dataOcorridoAte) {
		this.dataOcorridoAte = dataOcorridoAte;
	}
	public void setDataOcorrido(LocalDate dataOcorrido) {
		this.dataOcorrido = dataOcorrido;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
	public Alojamento getAlojamento() {
		return alojamento;
	}
	public void setAlojamento(Alojamento alojamento) {
		this.alojamento = alojamento;
	}
	public Integer getApartamento() {
		return apartamento;
	}
	public void setApartamento(Integer apartamento) {
		this.apartamento = apartamento;
	}
	
}
