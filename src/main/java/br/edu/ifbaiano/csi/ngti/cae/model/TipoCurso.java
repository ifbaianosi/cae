package br.edu.ifbaiano.csi.ngti.cae.model;

public enum TipoCurso {
	
	INTEGRADO("Médio Integrado"),
	SUBSEQUENTE("Médio Subsequente"),
	SUPERIOR("Superior");
	
	private String descricao;

	private TipoCurso(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
