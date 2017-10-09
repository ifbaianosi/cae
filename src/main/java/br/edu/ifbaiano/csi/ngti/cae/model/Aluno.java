package br.edu.ifbaiano.csi.ngti.cae.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

@Entity
@Table(name="aluno")
public class Aluno extends Entidade implements Paciente {

	private static final long serialVersionUID = 2970810779587217348L;

	// CAMPOS OBRIGATORIOS PARA CADASTRO RAPIDO =========================
	@NotBlank(message="A matricula é obrigatória")
	@Size(max=7, message="A matricula deve conter no máximo {max} números")
	private String matricula;
	
	@NotBlank(message="O nome é obrigatório")
	@Size(max=80, message="O nome deve conter no máximo {max} caracteres")
	private String nome;
	
	@NotNull(message="Selecione o regime do aluno")
	@Enumerated(EnumType.STRING)
	private Regime regime;
	
	@NotNull(message="Selecione um curso")
	@ManyToOne
	@JoinColumn(name="codigo_curso")
	private Curso curso;
	
	@NotNull(message="Selecione a serie/turma")
	@Enumerated(EnumType.STRING)
	@Column(name="serie_turma")
	private SerieTurma serieTurma;
	//====================================================================
	
	@Size(max=80, message="O nome social deve conter no máximo {max} caracteres")
	@Column(name="nome_social")
	private String nomeSocial;
	
	@Email(message="Email inválido")
	@Size(max=80, message="O email deve conter no máximo {max} caracteres")
	private String email;
	
	
	/**CAMPOS PARA INSERIR A FOTO DO ALUNO*/
	private String foto;

	@Column(name="content_type")
	private String contentType;
	/**FOTO*/
	
	//@NotNull(message="A data de nascimento é obrigatório")
	@Column(name="data_nascimento")
	private LocalDate dataNascimento;
	
	@Transient
	private Integer idade;
	
	//@NotNull(message="Selecione o sexo")
	@Enumerated(EnumType.STRING)
	private Sexo sexo;
	

	
	@Max(value=100, message="O número do apartamento deve ser no máximo até 100")
	private Integer apartamento;
	
	//@NotBlank(message="O número para contato é obrigatório")
	@Size(max=20, message="O número para contato deve conter no máximo {max} caracteres")
	private String contato;
	
	@Enumerated(EnumType.STRING)
	@Column(name="alojamento")
	private Alojamento alojamento;
	
	private Boolean whatsapp;
	
	private Boolean saida;//Saida do campus
	
	@Column(name="saida_sem_aula")
	private Boolean saidaSemAula;//Saida do campus durante o periodo sem aula
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	//TODO: Rever o mapeamento de responsaveis do aluno
	@Transient
	private List<ResponsavelAluno> responsaveisDoAluno;
	
	@Transient
	private Boolean temResponsavel;
	
	@PrePersist
	public void prePersist(){
		if(this.isNovo())
			status = Status.FREQUENTANDO;
	}
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Regime getRegime() {
		return regime;
	}
	public void setRegime(Regime regime) {
		this.regime = regime;
	}
	public Sexo getSexo() {
		return sexo;
	}
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public Integer getIdade() {
		return idade;
	}
	public void setIdade(Integer idade) {
		this.idade = idade;
	}
	public Curso getCurso() {
		return curso;
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	public Integer getApartamento() {
		return apartamento;
	}
	public void setApartamento(Integer apartamento) {
		this.apartamento = apartamento;
	}
	public SerieTurma getSerieTurma() {
		return serieTurma;
	}
	public void setSerieTurma(SerieTurma serieTurma) {
		this.serieTurma = serieTurma;
	}
	public String getContato() {
		return contato;
	}
	public void setContato(String contato) {
		this.contato = contato;
	}
	public Boolean getWhatsapp() {
		return whatsapp;
	}
	public void setWhatsapp(Boolean whatsapp) {
		this.whatsapp = whatsapp;
	}
	public Boolean getTemResponsavel() {
		return temResponsavel;
	}
	public void setTemResponsavel(Boolean temResponsavel) {
		this.temResponsavel = temResponsavel;
	}
	public List<ResponsavelAluno> getResponsaveisDoAluno() {
		return responsaveisDoAluno;
	}
	public void setResponsaveisDoAluno(List<ResponsavelAluno> responsaveisDoAluno) {
		this.responsaveisDoAluno = responsaveisDoAluno;
	}
	public Alojamento getAlojamento() {
		return alojamento;
	}
	public void setAlojamento(Alojamento alojamento) {
		this.alojamento = alojamento;
	}
	public Boolean getSaida() {
		return saida;
	}
	public void setSaida(Boolean saida) {
		this.saida = saida;
	}
	public String getNomeSocial() {
		return nomeSocial;
	}
	public void setNomeSocial(String nomeSocial) {
		this.nomeSocial = nomeSocial;
	}
	public String getFotoOuMock() {
		return !StringUtils.isEmpty(foto) ? foto : "aluno-mock.png";
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Boolean getSaidaSemAula() {
		return saidaSemAula;
	}
	
	public void setSaidaSemAula(Boolean saidaSemAula) {
		this.saidaSemAula = saidaSemAula;
	}
	
	public String getDataNascimentoFormatada() {
		DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return dataNascimento!=null ? dataNascimento.format(formater) : "";
	}

	public boolean isCadastroCompleto(){
		System.out.println("cadastroCompletoAluno: " + (serieTurma != null));
		return serieTurma != null;
	}
	
	public boolean podeRegistrarOcorrencia(){
		System.out.println("cadastroCompletoAluno: " + (serieTurma != null));
		return serieTurma != null && status == Status.FREQUENTANDO;
	}
	
}
