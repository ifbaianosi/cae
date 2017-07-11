package br.edu.ifbaiano.csi.ngti.cae.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import br.edu.ifbaiano.csi.ngti.cae.validation.AtributoConfirmacao;

@AtributoConfirmacao(atributo = "senha", atributoConfirmacao = "confirmacaoSenha", message = "Confirmação da senha não confere")
@Entity
@Table(name="usuario")
public class Usuario extends Entidade {

	private static final long serialVersionUID = -1704912817129220803L;

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
	
	private Boolean ativo;
	
	/*@NotNull(message="Data nascimento é obrigatório")*/
	@Column(name="data_nascimento")
	private LocalDate dataNascimento;
	
	/**
	 * GRUPO PODE SER ALTERADO NA VIEW PARA PERFIL
	 */
	@Size(min=1, message="Selecione pelo menos um perfil")
	@ManyToMany
	@JoinTable(name="usuario_grupo", joinColumns = @JoinColumn(name="codigo_usuario")
								   , inverseJoinColumns = @JoinColumn(name="codigo_grupo"))
	private List<Grupo> grupos;
	
	@PrePersist
	public void prePersist(){
		this.ativo = true;
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
	
}
