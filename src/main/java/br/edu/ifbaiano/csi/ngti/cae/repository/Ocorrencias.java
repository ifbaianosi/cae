package br.edu.ifbaiano.csi.ngti.cae.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.model.Ocorrencia;
import br.edu.ifbaiano.csi.ngti.cae.repository.filter.OcorrenciaFilter;
import br.edu.ifbaiano.csi.ngti.cae.repository.helper.ocorrencia.OcorrenciasQueries;

@Repository
public interface Ocorrencias extends JpaRepository<Ocorrencia, Long>, OcorrenciasQueries{

	public List<Ocorrencia> findByAlunoOrderByDataRegistroDesc(Aluno aluno);
	public Long countByAluno(Aluno aluno);
	public List<Ocorrencia> findByAluno(Aluno aluno);
}
