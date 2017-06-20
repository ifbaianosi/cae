package br.edu.ifbaiano.csi.ngti.cae.model;

public enum Alojamento {

	M("Masculino"),
	F("Feminino");
	
	private String descricao;
	
	private Alojamento(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
