package br.edu.ifbaiano.csi.ngti.cae.dto;

import java.time.LocalDate;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;

public class RelatorioOcorrencias {

	private LocalDate dataInicio;
	private LocalDate dataFim;
	private Aluno aluno;
	
	public LocalDate getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}
	public LocalDate getDataFim() {
		return dataFim;
	}
	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
}
