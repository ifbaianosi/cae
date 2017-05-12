package br.edu.ifbaiano.csi.ngti.cae.model;

public enum GrauParentesco {

	PAI("Pai"),
	MAE("Mãe"),
	AVOS("Avô/Avó"),
	PADASTRO("Padastro"),
	MADASTRA("Madastra"),
	IRMAO("Irmão/Irmã");
	
	private String descricao;
	
	private GrauParentesco(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
