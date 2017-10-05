package br.edu.ifbaiano.csi.ngti.cae.dto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.edu.ifbaiano.csi.ngti.cae.model.TipoNotificacao;

public class NotificacaoDTO {

	private Long codigo;
	private String descricao;
	private String data;
	private String uri;
	private String tipoNotificacao;
	private String tempo;
	
	public NotificacaoDTO(Long codigo, String descricao, LocalDateTime data, String uri, TipoNotificacao tipoNotificacao) {
		this.codigo = codigo;
		this.descricao = descricao;
		DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		this.data = data.format(formater);
		
		Duration dur = Duration.between(data, LocalDateTime.now());
		
		long temp = dur.getSeconds();
		this.tempo = dur.getSeconds() + " seg atrás";
		
		if(temp > 59 && temp < 3600)
			this.tempo = dur.toMinutes() + " min atrás";
		else if(temp > 3599 && temp < 86400)
			this.tempo = dur.toHours() + " hora(s) atrás";
		else if(temp > 86399)
			this.tempo = dur.toDays() + " dia(s) atrás";
		
		this.uri = uri;
		this.tipoNotificacao = tipoNotificacao.getDescricao();
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getTipoNotificacao() {
		return tipoNotificacao;
	}

	public void setTipoNotificacao(String tipoNotificacao) {
		this.tipoNotificacao = tipoNotificacao;
	}

	public String getTempo() {
		return tempo;
	}

	public void setTempo(String tempo) {
		this.tempo = tempo;
	}
	
}
