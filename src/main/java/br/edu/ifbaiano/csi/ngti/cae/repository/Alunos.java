package br.edu.ifbaiano.csi.ngti.cae.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;

@Repository
public interface Alunos extends JpaRepository<Aluno, Long>{

}
