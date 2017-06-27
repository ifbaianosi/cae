package br.edu.ifbaiano.csi.ngti.cae.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import br.edu.ifbaiano.csi.ngti.cae.model.Encaminhamento;
import br.edu.ifbaiano.csi.ngti.cae.model.Identificacao;
import br.edu.ifbaiano.csi.ngti.cae.model.SerieTurma;

public class OcorrenciaDTO {

	private Long codigo;
	private String dataRegistro;
	private String dataOcorrido;
	private String local;
	private String descricao;
	private String serie;
	private String identificacao;
	private List<Encaminhamento> encaminhamentos;
	
	private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	
	public OcorrenciaDTO(Long codigo, LocalDateTime dataRegistro, LocalDateTime dataOcorrido, String local, String descricao, String serie, String identificacao) {
		this.codigo = codigo;
		this.dataRegistro = dataRegistro.format(formatador);
		this.dataOcorrido = dataOcorrido.format(formatador);
		this.local = local;
		this.descricao = descricao;
		this.serie = serie;
		this.identificacao = identificacao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDataOcorrido() {
		return dataOcorrido;
	}

	public void setDataOcorrido(LocalDateTime dataOcorrido) {
		this.dataOcorrido = dataOcorrido.format(formatador);
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(String dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public List<Encaminhamento> getEncaminhamentos() {
		return encaminhamentos;
	}

	public void setEncaminhamentos(List<Encaminhamento> encaminhamentos) {
		this.encaminhamentos = encaminhamentos;
	}
	
	
}
