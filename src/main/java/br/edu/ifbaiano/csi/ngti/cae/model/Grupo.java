package br.edu.ifbaiano.csi.ngti.cae.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="grupo")
@DynamicUpdate
public class Grupo extends Entidade {

	private static final long serialVersionUID = -2462044392127090103L;

	@NotBlank(message="O Nome é obrigatório")
	@Size(max=50, message="O nome deve conter no máximo {max} caracteres")
	private String nome;
	
	@NotBlank(message="A descrição é obrigatória")
	@Size(max=200, message="A descrição deve conter no máximo {max} caracteres")
	private String descricao;
	
	/*@NotNull(message="Selecione pelo menos uma permissão de acesso")*/
	@Size(min=1, message="Selecione pelo menos uma permissão de acesso")
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="grupo_permissao", joinColumns = @JoinColumn(name="codigo_grupo")
									 , inverseJoinColumns = @JoinColumn(name="codigo_permissao"))
	private List<Permissao> permissoes;

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Permissao> getPermissoes() {
		return permissoes;
	}
	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
