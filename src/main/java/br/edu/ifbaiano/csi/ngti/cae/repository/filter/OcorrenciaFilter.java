package br.edu.ifbaiano.csi.ngti.cae.repository.filter;

import java.time.LocalDateTime;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;

public class OcorrenciaFilter {

	private LocalDateTime dataOcorrido;
	private String local;
	private Aluno aluno;
	
	public LocalDateTime getDataOcorrido() {
		return dataOcorrido;
	}
	public void setDataOcorrido(LocalDateTime dataOcorrido) {
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
