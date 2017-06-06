package br.edu.ifbaiano.csi.ngti.cae.repository.filter;

import java.time.LocalDate;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;

public class OcorrenciaFilter {

	private LocalDate dataOcorrido;
	private LocalDate dataOcorridoAte;
	private String local;
	private Aluno aluno;
	
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
	
}
