package br.edu.ifbaiano.csi.ngti.cae.model;

public enum TipoEncaminhamento {
	
	ADVERTENCIA_ESCRITA("Advertência escrita"),
	ADVERTENCIA_VERBAL("Advertência verbal"),
	CONVOCACAO_PAIS("Convocação dos pais"),
	SUSPENSAO("Suspensão"),
	DESLIGADO("Desligado"),
	COMISSAO_DISCIPLINAR("Comissão disciplinar");
	
	private String descricao;
	
	private TipoEncaminhamento(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
