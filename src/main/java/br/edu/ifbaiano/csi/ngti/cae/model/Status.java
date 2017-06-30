package br.edu.ifbaiano.csi.ngti.cae.model;

public enum Status {

	FREQUENTANDO("Frequentando", "bg-teal"),
	DESLIGADO("Desligado", "bg-deep-orange"),
	CONCLUINTE("Concluinte", "bg-green"),
	DESISTENTE("Desistente", "bg-orange"),
	SUSPENSO("Suspenso", "bg-red");
	
	private String descricao;
	private String cor;
	
	private Status(String descricao, String cor) {
		this.descricao = descricao;
		this.cor = cor;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public String getCor() {
		return cor;
	}
}
