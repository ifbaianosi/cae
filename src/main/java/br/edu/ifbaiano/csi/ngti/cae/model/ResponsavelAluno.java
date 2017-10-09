package br.edu.ifbaiano.csi.ngti.cae.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="responsavel_aluno")
public class ResponsavelAluno extends Entidade{

	private static final long serialVersionUID = -5828982760072872399L;

	/*@EmbeddedId
	private ResponsavelAlunoID id;*/
	
	@NotNull(message="O aluno é obrigatório")
	@ManyToOne
	@JoinColumn(name="codigo_aluno")
	private Aluno aluno;

	@NotNull(message="O responsável é obrigatório")
	@ManyToOne
	@JoinColumn(name="codigo_responsavel")
	private Responsavel responsavel;
	
	@NotNull(message="O grau de parentesco é obrigatório")
	@Enumerated(EnumType.STRING)
	private GrauParentesco parentesco;

	public GrauParentesco getParentesco() {
		return parentesco;
	}

	public void setParentesco(GrauParentesco parentesco) {
		this.parentesco = parentesco;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	
	
}
