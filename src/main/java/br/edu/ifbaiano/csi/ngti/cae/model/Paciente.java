package br.edu.ifbaiano.csi.ngti.cae.model;

import java.time.LocalDate;

public interface Paciente {

	public Long getCodigo();
	public String getNome();
	public LocalDate getDataNascimento();
	public String getMatricula();
}
