package br.edu.ifbaiano.csi.ngti.cae.model;

public enum UsuarioStatus {

	Ativo("bg-teal"),
	Inativo("bg-deep-orange");
	
	private String cor;
	
	private UsuarioStatus(String cor) {
		this.cor = cor;
	}
	
	public String getCor() {
		return cor;
	}
}
