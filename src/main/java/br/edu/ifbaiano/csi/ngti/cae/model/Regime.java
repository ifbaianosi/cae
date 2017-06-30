package br.edu.ifbaiano.csi.ngti.cae.model;

public enum Regime {

	INTERNO("Aluno Interno"),
	SEMI_INTERNO("Aluno Semi-Interno"),
	EXTERNO("Aluno Externo");
	
	private String descricao;
	
	private Regime(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
