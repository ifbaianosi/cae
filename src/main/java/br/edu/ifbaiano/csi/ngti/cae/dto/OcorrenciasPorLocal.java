package br.edu.ifbaiano.csi.ngti.cae.dto;

public class OcorrenciasPorLocal {

	private String local;
	private Integer ocorrencias;
	
	
	public OcorrenciasPorLocal() {
	}

	public OcorrenciasPorLocal(String local, Integer ocorrencias) {
		this.local = local;
		this.ocorrencias = ocorrencias;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public Integer getOcorrencias() {
		return ocorrencias;
	}

	public void setOcorrencias(Integer ocorrencias) {
		this.ocorrencias = ocorrencias;
	}

	
	
}
