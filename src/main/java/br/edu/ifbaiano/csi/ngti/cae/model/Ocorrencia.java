package br.edu.ifbaiano.csi.ngti.cae.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="ocorrencia")
@DynamicUpdate
public class Ocorrencia extends Entidade{

	private static final long serialVersionUID = -4539578952663353947L;

	@Column(name="data_registro")
	private LocalDateTime dataRegistro;
	
	@NotNull(message="A data da ocorrência é obrigatória")
	@Column(name="data_ocorrido")
	private LocalDateTime dataOcorrido;
	
	@NotBlank(message="A descrição é obrigatória")
	@Size(max=500, message="A descrição deve conter no máximo {max} caracteres")
	private String descricao;
	
	@NotBlank(message="O local da ocorrência é obrigatório")
	@Column(name="local_ocorrencia")
	@Size(max=200, message="A descricao deve conter no máximo {max} caracteres")
	private String local;
	
	/*@NotNull(message="O aluno é obrigatório")*/
	@ManyToOne
	@JoinColumn(name="codigo_aluno")
	private Aluno aluno;
	
	@NotNull(message="Selecione pelo menos um aluno")
	@Size(min=1, message="Selecione pelo menos um aluno")
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	@JoinTable(name="ocorrencia_aluno", joinColumns = @JoinColumn(name="codigo_ocorrencia")
								   , inverseJoinColumns = @JoinColumn(name="codigo_aluno"))
	private List<Aluno> alunos;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codigo_usuario")
	private Usuario usuario;
	
	private String serie;
	
	private String regime;
	
	@OneToMany(mappedBy = "ocorrencia", fetch=FetchType.LAZY)
	private List<Encaminhamento> encaminhamentos;
	
	@Transient
	private String uuid;
	
	//ocorrencia coletiva
	@NotNull(message="Informe o tipo da ocorrência")
	private Boolean coletiva = false; 

	@PrePersist
	public void prePersist(){
		if(this.dataRegistro == null)
			this.dataRegistro = LocalDateTime.now();
	}

	public LocalDateTime getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(LocalDateTime dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public LocalDateTime getDataOcorrido() {
		return dataOcorrido;
	}

	public void setDataOcorrido(LocalDateTime dataOcorrido) {
		this.dataOcorrido = dataOcorrido;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public List<Encaminhamento> getEncaminhamentos() {
		return encaminhamentos;
	}

	public void setEncaminhamentos(List<Encaminhamento> encaminhamentos) {
		this.encaminhamentos = encaminhamentos;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public Boolean getColetiva() {
		return coletiva;
	}

	public void setColetiva(Boolean coletiva) {
		this.coletiva = coletiva;
	}

	@Transient
	private DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	
	public String getDataOcorridoFormatada() {
		return dataOcorrido.format(formater);
	}
	
	public String getDataRegistroFormatada() {
		return dataRegistro.format(formater);
	}
	
	public int getQuantidadeAlunos(){
		return alunos.size();
	}
	
	public String getDescricaoResumida(){
		String descr = this.descricao;
		if(this.descricao.length() > 80){
			descr = this.descricao.substring(0, 80).concat("...");
		}
		
		return descr;
	}
	
	public String getLocalResumido(){
		String lcl = this.local;
		if(this.local.length() > 20){
			lcl = this.local.substring(0, 20).concat("...");
		}
		
		return lcl;
	}
	
}
