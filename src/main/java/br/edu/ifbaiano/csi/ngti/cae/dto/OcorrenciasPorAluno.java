package br.edu.ifbaiano.csi.ngti.cae.dto;

public class OcorrenciasPorAluno {

	private String aluno;
	private Integer ocorrencias;
	
	
	public OcorrenciasPorAluno() {
	}

	public OcorrenciasPorAluno(String aluno, Integer ocorrencias) {
		this.aluno = aluno;
		this.ocorrencias = ocorrencias;
	}

	public String getAluno() {
		return aluno;
	}

	public void setAluno(String aluno) {
		this.aluno = aluno;
	}

	public Integer getOcorrencias() {
		return ocorrencias;
	}

	public void setOcorrencias(Integer ocorrencias) {
		this.ocorrencias = ocorrencias;
	}

	
	
}
