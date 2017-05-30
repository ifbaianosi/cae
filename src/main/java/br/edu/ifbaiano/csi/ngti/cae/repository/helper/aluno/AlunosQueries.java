package br.edu.ifbaiano.csi.ngti.cae.repository.helper.aluno;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.repository.filter.AlunoFilter;

public interface AlunosQueries {

	public Page<Aluno> filtrar(AlunoFilter alunoFilter, Pageable pageable);
}
