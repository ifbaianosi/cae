package br.edu.ifbaiano.csi.ngti.cae.repository.helper.aluno;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.edu.ifbaiano.csi.ngti.cae.dto.AlunoDTO;
import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.repository.filter.AlunoFilter;

public interface AlunosQueries {

	public Page<Aluno> filtrar(AlunoFilter alunoFilter, Pageable pageable);
	public List<Aluno> porNomeOuMatricula(String nomeOuMatricula);
	public Optional<AlunoDTO> porMatricula(String matricula);
	public List<Aluno> filtroAdicionarNaOcorrencia(AlunoFilter alunoFilter);
}
