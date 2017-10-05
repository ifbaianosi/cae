package br.edu.ifbaiano.csi.ngti.cae.dto;

public class CodigoNotificacaoVisualizadaDTO {
	
	private Long codigoNotificacao;
	
	public CodigoNotificacaoVisualizadaDTO(Long codigoNotificacao) {
		this.codigoNotificacao = codigoNotificacao;
	}

	public Long getCodigoNotificacao() {
		return codigoNotificacao;
	}

	public void setCodigoNotificacao(Long codigoNotificacao) {
		this.codigoNotificacao = codigoNotificacao;
	}
}
