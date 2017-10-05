package br.edu.ifbaiano.csi.ngti.cae.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="encaminhamento")
public class Encaminhamento extends Entidade {

	private static final long serialVersionUID = 92198494496818071L;

	@NotBlank(message="A descrição é obrigatória.")
	@Size(max=250, message="A descrição deve conter no máximo {max} caracteres.")
	private String descricao;
	
	@Column(name="data_encaminhamento")
	private LocalDateTime dataEncaminhamento;
	
	@NotNull(message="Selecione o tipo de encaminhamento.")
	@Enumerated(EnumType.STRING)
	@Column(name="tipo_encaminhamento")
	private TipoEncaminhamento tipoEncaminhamento;
	
	@NotNull(message="Uma ocorrencia é necessária para criar um encaminhamento.")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigo_ocorrencia")
	private Ocorrencia ocorrencia;
	
	@PrePersist
	public void prePersist(){
		this.dataEncaminhamento = LocalDateTime.now();
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public LocalDateTime getDataEncaminhamento() {
		return dataEncaminhamento;
	}
	public void setDataEncaminhamento(LocalDateTime dataEncaminhamento) {
		this.dataEncaminhamento = dataEncaminhamento;
	}
	public TipoEncaminhamento getTipoEncaminhamento() {
		return tipoEncaminhamento;
	}
	public void setTipoEncaminhamento(TipoEncaminhamento tipoEncaminhamento) {
		this.tipoEncaminhamento = tipoEncaminhamento;
	}
	public Ocorrencia getOcorrencia() {
		return ocorrencia;
	}
	public void setOcorrencia(Ocorrencia ocorrencia) {
		this.ocorrencia = ocorrencia;
	}
	
}
