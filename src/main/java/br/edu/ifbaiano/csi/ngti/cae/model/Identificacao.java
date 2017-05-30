package br.edu.ifbaiano.csi.ngti.cae.model;

public enum Identificacao {

	INTERNO("Aluno Interno"),
	SEMI_INTERNO("Aluno Semi-Interno"),
	EXTERNO("Aluno Externo"),
	/*FUNCIONARIO("Funcionário Terceirizado"),
	SERVIDOR("Servidor"),
	NAO_IDENTIFICADO("Não Identificado")*/;
	
	private String descricao;
	
	private Identificacao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
