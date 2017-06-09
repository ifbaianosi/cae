package br.edu.ifbaiano.csi.ngti.cae.model;

public enum TipoEncaminhamento {
	
	ADVERTENCIA_ESCRITA("Advertência escrita"),
	CONVOCACAO_PAIS("Convocação dos pais"),
	SUSPENSAO("Suspensão");
	
	private String descricao;
	
	private TipoEncaminhamento(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
