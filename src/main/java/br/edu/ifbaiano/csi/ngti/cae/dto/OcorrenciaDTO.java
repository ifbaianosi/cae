package br.edu.ifbaiano.csi.ngti.cae.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import br.edu.ifbaiano.csi.ngti.cae.model.Encaminhamento;
import br.edu.ifbaiano.csi.ngti.cae.model.Regime;
import br.edu.ifbaiano.csi.ngti.cae.model.SerieTurma;

public class OcorrenciaDTO {

	private Long codigo;
	private String dataRegistro;
	private String dataOcorrido;
	private String local;
	private String descricao;
	private String serie;
	private String regime;
	private List<Encaminhamento> encaminhamentos;
	
	private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	
	public OcorrenciaDTO(Long codigo, LocalDateTime dataRegistro, LocalDateTime dataOcorrido, String local, String descricao, String serie, String regime) {
		this.codigo = codigo;
		this.dataRegistro = dataRegistro.format(formatador);
		this.dataOcorrido = dataOcorrido.format(formatador);
		this.local = local;
		this.descricao = descricao;
		this.serie = serie;
		this.regime = regime;
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

	public String getRegime() {
		return regime;
	}

	public void setRegime(String regime) {
		this.regime = regime;
	}

	public List<Encaminhamento> getEncaminhamentos() {
		return encaminhamentos;
	}

	public void setEncaminhamentos(List<Encaminhamento> encaminhamentos) {
		this.encaminhamentos = encaminhamentos;
	}
	
	
}
