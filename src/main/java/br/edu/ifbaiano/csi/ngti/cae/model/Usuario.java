package br.edu.ifbaiano.csi.ngti.cae.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.edu.ifbaiano.csi.ngti.cae.validation.AtributoConfirmacao;

@AtributoConfirmacao(atributo = "senha", atributoConfirmacao = "confirmacaoSenha", message = "Confirmação da senha não confere")
@Entity
@Table(name="usuario")
@DynamicUpdate
public class Usuario implements Serializable{

	private static final long serialVersionUID = -1704912817129220803L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotBlank(message="Nome é obrigatório")
	@Size(max=50, message="O nome deve conter no máximo {max} caracteres")
	private String nome;
	
	@NotBlank(message="E-mail é obrigatório")
	@Email(message="E-mail inválido")
	@Size(max=50, message="O nome deve conter no máximo {max} caracteres")
	private String email;
	
	/* Validação especifica futuramente */
	private String senha;
	
	@Transient
	private String confirmacaoSenha;
	
	@Transient
	private UsuarioStatus status;
	
	private Boolean ativo = true;
	
	@Column(name="data_nascimento")
	private LocalDate dataNascimento;
	
	@NotNull(message="Selecione pelo menos um perfil")
	/*@Size(min=1, message="Selecione pelo menos um perfil")*/
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	@JoinTable(name="usuario_grupo", joinColumns = @JoinColumn(name="codigo_usuario")
								   , inverseJoinColumns = @JoinColumn(name="codigo_grupo"))
	@JsonIgnoreProperties
	private List<Grupo> grupos;
	
	@PrePersist
	public void prePersist(){
		this.ativo = true;
	}
	
	@PreUpdate
	private void preUpdate() {
		this.confirmacaoSenha = senha;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public List<Grupo> getGrupos() {
		return grupos;
	}
	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}
	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}
	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}
	
	public String getPrimeiroNomeEmail(){
		return email.substring(0, email.indexOf("@")).trim();
	}

	public String getDataNascimentoFormatada() {
		DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return dataNascimento!=null ? dataNascimento.format(formater) : "";
	}
	
	public UsuarioStatus getAtivoOuInativo(){
		return ativo == true ? UsuarioStatus.Ativo : UsuarioStatus.Inativo;
	}
	
	public boolean isNovo(){
		return codigo == null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
	
}
