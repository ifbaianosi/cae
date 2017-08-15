package br.edu.ifbaiano.csi.ngti.cae.dto;

public class OcorrenciasPorUsuario {

	private String usuario;
	private Integer ocorrencias;
	
	
	public OcorrenciasPorUsuario() {
	}

	public OcorrenciasPorUsuario(String usuario, Integer ocorrencias) {
		this.usuario = usuario;
		this.ocorrencias = ocorrencias;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Integer getOcorrencias() {
		return ocorrencias;
	}

	public void setOcorrencias(Integer ocorrencias) {
		this.ocorrencias = ocorrencias;
	}

	
	
}
