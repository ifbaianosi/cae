package br.edu.ifbaiano.csi.ngti.cae.repository.filter;

import java.time.LocalDate;
import java.util.List;

import br.edu.ifbaiano.csi.ngti.cae.model.Grupo;

public class UsuarioFilter {

	private String nome;
	private String email;
	private LocalDate dataNascimento;
	private List<Grupo> grupos;
	
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
	
	
}
