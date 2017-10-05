package br.edu.ifbaiano.csi.ngti.cae.model;

import java.time.LocalDate;

public class Funcionario extends Entidade implements Paciente {

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String matricula;
	private LocalDate dataNascimento;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Override
	public String getNome() {
		return nome;
	}

	@Override
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	@Override
	public String getMatricula() {
		return matricula;
	}

}
