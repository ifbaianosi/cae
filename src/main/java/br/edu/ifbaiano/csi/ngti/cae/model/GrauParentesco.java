package br.edu.ifbaiano.csi.ngti.cae.model;

public enum GrauParentesco {

	PAI("Pai"),
	MAE("Mãe"),
	AVO("Avô/Avó"),
	TIO("Tio/Tia"),
	PADASTRO("Padastro"),
	MADASTRA("Madastra"),
	IRMAO("Irmão/Irmã"),
	OUTROS("Outros");
	
	private String descricao;
	
	private GrauParentesco(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
