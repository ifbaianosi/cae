package br.edu.ifbaiano.csi.ngti.cae.model;

public enum SerieTurma {

	_1A("1º ano A" ,"1a"),
	_1B("1º ano B" ,"1b"),
	_1C("1º ano C" ,"1c"),
	_1D("1º ano D" ,"1d"),
	_1E("1º ano E" ,"1e"),
	_1F("1º ano F" ,"1f"),
	_1G("1º ano G" ,"1g"),
	_1H("1º ano H" ,"1h"),
	
	_2A("2º ano A" ,"2a"),
	_2B("2º ano B" ,"2b"),
	_2C("2º ano C" ,"2a"),
	_2D("2º ano D" ,"2a"),
	_2E("2º ano E" ,"2a"),
	_2F("2º ano F" ,"2a"),
	_2G("2º ano G" ,"2a"),
	_2H("2º ano H" ,"2a"),
	
	_3A("3º ano A" ,"3a"),
	_3B("3º ano B" ,"3b"),
	_3C("3º ano C" ,"3c"),
	_3D("3º ano D" ,"3d"),
	_3E("3º ano E" ,"3e"),
	_3F("3º ano F" ,"3f"),
	_3G("3º ano G" ,"3g"),
	_3H("3º ano H" ,"3h");
	
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
