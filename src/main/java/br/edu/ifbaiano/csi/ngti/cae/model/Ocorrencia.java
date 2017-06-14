package br.edu.ifbaiano.csi.ngti.cae.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
	
	@NotNull(message="A data do ocorrido e obrigatoria")
	@Column(name="data_ocorrido")
	private LocalDateTime dataOcorrido;
	
	@NotBlank(message="A descricao e obrigatoria")
	@Size(max=254, message="A descricao deve conter no maximo {max} caracteres")
	private String descricao;
	
	@Column(name="local_ocorrencia")
	@Size(max=200, message="A descricao deve conter no maximo {max} caracteres")
	private String local;
	
	@NotNull(message="O aluno e obrigatorio")
	@ManyToOne
	@JoinColumn(name="codigo_aluno")
	private Aluno aluno;
	
	@OneToMany(mappedBy = "ocorrencia", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Encaminhamento> encaminhamentos = new ArrayList<>();

	@PrePersist
	public void prePersist(){
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
	
	@Transient
	private DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	
	public String getDataOcorridoFormatada() {
		return dataOcorrido.format(formater);
	}
	
	public String getDataRegistroFormatada() {
		return dataRegistro.format(formater);
	}
	
}
