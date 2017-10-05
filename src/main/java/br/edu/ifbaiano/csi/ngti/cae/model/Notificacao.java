package br.edu.ifbaiano.csi.ngti.cae.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="notificacao")
public class Notificacao extends Entidade{

	private static final long serialVersionUID = 7764300895830178323L;

	@NotBlank(message="A descrição é obrigatória")
	@Size(max=180, message="O nome deve conter no máximo {max} caracteres")
	private String descricao;
	
	@NotNull(message="A data é obrigatória")
	private LocalDateTime data;
	
	@NotBlank(message="A sigla é obrigatória")
	@Size(max=80, message="A sigla deve conter no máximo {max} caracteres")
	private String uri;
	

	@NotNull(message="Selecione o tipo do curso")
	@Enumerated(EnumType.STRING)
	@Column(name="tipo")
	private TipoNotificacao tipoNotificacao;


	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public TipoNotificacao getTipoNotificacao() {
		return tipoNotificacao;
	}

	public void setTipoNotificacao(TipoNotificacao tipoNotificacao) {
		this.tipoNotificacao = tipoNotificacao;
	}
	
	@Transient
	private DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	
	public String getDataFormatada() {
		return data.format(formater);
	}
	
}
