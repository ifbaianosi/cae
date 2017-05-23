package br.edu.ifbaiano.csi.ngti.cae.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.model.Ocorrencia;

@Repository
public interface Ocorrencias extends JpaRepository<Ocorrencia, Long>{

	public List<Ocorrencia> findByAlunoOrderByDataRegistroDesc(Aluno aluno);
}
