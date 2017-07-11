package br.edu.ifbaiano.csi.ngti.cae.repository.filter;

import java.time.LocalDate;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.model.Usuario;

public class OcorrenciaFilter {

	private LocalDate dataOcorrido;
	private LocalDate dataOcorridoAte;
	private String local;
	private Aluno aluno;
	private Usuario usuario;
	
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
	
}
