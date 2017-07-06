package br.edu.ifbaiano.csi.ngti.cae.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="grupo")
public class Grupo extends Entidade {

	private static final long serialVersionUID = -2462044392127090103L;

	@NotBlank(message="Nome é obrigatório")
	@Size(max=50, message="O nome deve conter no máximo {max} caracteres")
	private String nome;
	
	@ManyToMany
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
}
