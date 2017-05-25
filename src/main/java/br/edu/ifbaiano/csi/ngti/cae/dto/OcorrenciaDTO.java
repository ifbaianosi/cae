package br.edu.ifbaiano.csi.ngti.cae.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OcorrenciaDTO {

	private Long codigo;
	private String dataOcorrido;
	private String local;
	private String descricao;
	
	private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	
	public OcorrenciaDTO(Long codigo, LocalDateTime dataOcorrido, String local, String descricao) {
		this.codigo = codigo;
		this.dataOcorrido = dataOcorrido.format(formatador);
		this.local = local;
		this.descricao = descricao;
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
	
	
}
