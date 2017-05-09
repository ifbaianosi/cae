package br.edu.ifbaiano.csi.ngti.cae.model;

public enum Sexo {

	MASCULINO("Masculino"),
	FEMININO("Feminino");
	
	private String descricao;
	
	private Sexo(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
