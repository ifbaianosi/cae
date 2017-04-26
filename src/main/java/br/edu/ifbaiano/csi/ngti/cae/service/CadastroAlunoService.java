package br.edu.ifbaiano.csi.ngti.cae.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.repository.Alunos;

@Service
public class CadastroAlunoService {

	@Autowired
	private Alunos alunos;
	
	@Transactional
	public void salvar(Aluno aluno){
		alunos.save(aluno);
	}

	/*@Transactional
	public void excluir(Aluno aluno) {
		try {
			alunos.delete(aluno);
			alunos.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar aluno. Já existe matrículas efetuadas.");
		}
	}*/
}
