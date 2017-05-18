package br.edu.ifbaiano.csi.ngti.cae.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name="responsavel_aluno")
public class ResponsavelAluno {

	@EmbeddedId
	private ResponsavelAlunoID id;
	
	@Enumerated(EnumType.STRING)
	private GrauParentesco parentesco;

	public ResponsavelAlunoID getId() {
		return id;
	}

	public void setId(ResponsavelAlunoID id) {
		this.id = id;
	}

	public GrauParentesco getParentesco() {
		return parentesco;
	}

	public void setParentesco(GrauParentesco parentesco) {
		this.parentesco = parentesco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ResponsavelAluno other = (ResponsavelAluno) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
