package br.edu.ifbaiano.csi.ngti.cae.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.model.Encaminhamento;
import br.edu.ifbaiano.csi.ngti.cae.model.Ocorrencia;
import br.edu.ifbaiano.csi.ngti.cae.model.Regime;
import br.edu.ifbaiano.csi.ngti.cae.model.SerieTurma;
import br.edu.ifbaiano.csi.ngti.cae.model.Usuario;

public class OcorrenciaDTO {

	private Long codigo;
	private String aluno;
	private String dataRegistro;
	private String dataOcorrido;
	private String local;
	private String descricao;
	private String serie;
	private String regime;
	private String usuario;
	private String tipoEncaminhamento;
	private String descricaoEncaminhamento;
	private String tipoOcorrencia;
	private List<AlunoDTO> alunos = new ArrayList<AlunoDTO>();
	
	private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	
	public OcorrenciaDTO(Ocorrencia ocorrencia) {
		this.codigo = ocorrencia.getCodigo();
		this.aluno = ocorrencia.getAluno().getNome();
		this.dataRegistro = ocorrencia.getDataRegistro().format(formatador);
		this.dataOcorrido = ocorrencia.getDataOcorrido().format(formatador);
		this.local = ocorrencia.getLocal();
		this.descricao = ocorrencia.getDescricao();
		this.serie = ocorrencia.getSerie();
		this.regime = ocorrencia.getRegime();
		this.usuario = ocorrencia.getUsuario().getPrimeiroNomeEmail();
		this.tipoOcorrencia = ocorrencia.getColetiva() == null || ocorrencia.getColetiva()  == false ? "Individual" : "Coletiva";
		for(Aluno aluno: ocorrencia.getAlunos()){
			System.out.println("aluno: "+aluno.getMatricula());
			this.alunos.add(new AlunoDTO(aluno));
		}
		System.out.println("alunos: "+alunos.size());
		/*this.tipoEncaminhamento = ocorrencia.getEncaminhamentos().size() > 0 ? ocorrencia.getEncaminhamentos().get(0).getTipoEncaminhamento().getDescricao() : "";
		this.descricaoEncaminhamento = ocorrencia.getEncaminhamentos().size() > 0 ? ocorrencia.getEncaminhamentos().get(0).getDescricao() : "";*/
	}
	
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getTipoEncaminhamento() {
		return tipoEncaminhamento;
	}

	public void setTipoEncaminhamento(String tipoEncaminhamento) {
		this.tipoEncaminhamento = tipoEncaminhamento;
	}

	public String getDescricaoEncaminhamento() {
		return descricaoEncaminhamento;
	}

	public void setDescricaoEncaminhamento(String descricaoEncaminhamento) {
		this.descricaoEncaminhamento = descricaoEncaminhamento;
	}

	public List<AlunoDTO> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<AlunoDTO> alunos) {
		this.alunos = alunos;
	}

	public String getTipoOcorrencia() {
		return tipoOcorrencia;
	}

	public void setTipoOcorrencia(String tipoOcorrencia) {
		this.tipoOcorrencia = tipoOcorrencia;
	}

	public String getAluno() {
		return aluno;
	}

	public void setAluno(String aluno) {
		this.aluno = aluno;
	}

}
