package br.edu.ifbaiano.csi.ngti.cae.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifbaiano.csi.ngti.cae.model.ResponsavelAluno;
import br.edu.ifbaiano.csi.ngti.cae.repository.ResponsavelAlunos;

@Service
public class CadastroResponsavelAlunoService {
	
	@Autowired
	private ResponsavelAlunos responsavelAlunos;

	@Transactional
	public void salvar(ResponsavelAluno responsavelAluno){
		responsavelAlunos.save(responsavelAluno);
	}

	/*
	 * Excluir responsaval do aluno na tabela associativa (responsavel_aluno)
	 */
	@Transactional
	public void excluir(Long codigo) {
		responsavelAlunos.delete(codigo);
	}
}
