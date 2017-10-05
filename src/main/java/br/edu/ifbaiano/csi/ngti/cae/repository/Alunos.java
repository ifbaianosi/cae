package br.edu.ifbaiano.csi.ngti.cae.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.repository.helper.aluno.AlunosQueries;

@Repository
public interface Alunos extends JpaRepository<Aluno, Long>, AlunosQueries{

	public Optional<Aluno> findByMatricula(String matricula);
	public List<Aluno> findByCodigoIn(List<Long> codigos);
}
