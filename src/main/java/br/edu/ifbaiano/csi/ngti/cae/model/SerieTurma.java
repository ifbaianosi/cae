package br.edu.ifbaiano.csi.ngti.cae.model;

public enum SerieTurma {

	_1_A("1º ano A" ,"1a"),
	_1_B("1º ano B" ,"1b"),
	_1_C("1º ano C" ,"1c"),
	_1_D("1º ano D" ,"1d"),
	_1_E("1º ano E" ,"1e"),
	_1_F("1º ano F" ,"1f"),
	_1_G("1º ano G" ,"1g"),
	_1_H("1º ano H" ,"1h"),
	
	_2_A("2º ano A" ,"2a"),
	_2_B("2º ano B" ,"2b"),
	_2_C("2º ano C" ,"2a"),
	_2_D("2º ano D" ,"2a"),
	_2_E("2º ano E" ,"2a"),
	_2_F("2º ano F" ,"2a"),
	_2_G("2º ano G" ,"2a"),
	_2_H("2º ano H" ,"2a"),
	
	_3_A("3º ano A" ,"3a"),
	_3_B("3º ano B" ,"3b"),
	_3_C("3º ano C" ,"3c"),
	_3_D("3º ano D" ,"3d"),
	_3_E("3º ano E" ,"3e"),
	_3_F("3º ano F" ,"3f"),
	_3_G("3º ano G" ,"3g"),
	_3_H("3º ano H" ,"3h");
	
	private SerieTurma(String descricao, String palavrachave) {
		this.descricao = descricao;
		this.palavrachave = palavrachave;
	}
	
	private String descricao;
	private String palavrachave;
	
	public String getDescricao() {
		return descricao;
	}
	
	public String getPalavrachave() {
		return palavrachave;
	}
	
}
