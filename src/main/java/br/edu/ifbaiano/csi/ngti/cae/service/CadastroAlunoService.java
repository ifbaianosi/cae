package br.edu.ifbaiano.csi.ngti.cae.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.model.Responsavel;
import br.edu.ifbaiano.csi.ngti.cae.model.ResponsavelAluno;
import br.edu.ifbaiano.csi.ngti.cae.model.ResponsavelAlunoID;
import br.edu.ifbaiano.csi.ngti.cae.model.ResponsavelSession;
import br.edu.ifbaiano.csi.ngti.cae.repository.Alunos;
import br.edu.ifbaiano.csi.ngti.cae.repository.Responsaveis;
import br.edu.ifbaiano.csi.ngti.cae.repository.ResponsavelAlunos;
import br.edu.ifbaiano.csi.ngti.cae.service.exception.AlunoNumeroMatriculaJaCadastradoException;
import br.edu.ifbaiano.csi.ngti.cae.session.TabelasResponsaveisSession;

@Service
public class CadastroAlunoService {

	@Autowired
	private Alunos alunos;
	
	@Autowired
	private TabelasResponsaveisSession tabelasResponsaveisSession;
	
	@Autowired
	private Responsaveis responsaveis;
	
	@Autowired
	private ResponsavelAlunos responsavelAlunos;
	
	@Transactional
	public void salvar(Aluno aluno, String uuid){
		//TODO: AINDA FALTA EDITAR UM ALUNO...
		
		//verifica se já existe um aluno cadastrado com a matricula informada
		Optional<Aluno> alunoOptional = alunos.findByMatricula(aluno.getMatricula());
		if(alunoOptional.isPresent())
			throw new AlunoNumeroMatriculaJaCadastradoException("Já existe um aluno cadastrado com o numero da matriucla informado");
		
		//Salvar o aluno...
		alunos.save(aluno);
		
		//salvar os responsaveis que estão na sessão...
		List<ResponsavelSession> responsaveisSession = tabelasResponsaveisSession.getResponsaveis(uuid);
		for(ResponsavelSession rs: responsaveisSession){
			if(rs.getResponsavel().isNovo()){
				Optional<Responsavel> responsavelOptional = responsaveis.findByContato(rs.getResponsavel().getContato());
				if(!responsavelOptional.isPresent())
					responsaveis.saveAndFlush(rs.getResponsavel());
			}
		}
		
		//salvar na tabela responsavel_aluno...
		ResponsavelAlunoID responsavelAlunoID = new ResponsavelAlunoID();
		ResponsavelAluno responsavelAluno = new ResponsavelAluno();
		
		//recupera do banco de dados o aluno matriculado
		Aluno a = alunos.findByMatricula(aluno.getMatricula()).get();
		
		//seta o aluno recuperado no model responsavelAlunoID
		responsavelAlunoID.setAluno(a);
		
		
		//recuperar da sessao os responsaveis para recuperar do banco com o id
		for(ResponsavelSession rs: responsaveisSession){
			Optional<Responsavel> responsavelOptional = responsaveis.findByContato(rs.getResponsavel().getContato());
			if(responsavelOptional.isPresent()){
				//seta o responsavel recuperado no model responsavelAlunoID
				responsavelAlunoID.setResponsavel(responsavelOptional.get());
				
				//seta o model responsavelAlunoID
				responsavelAluno.setId(responsavelAlunoID);
				responsavelAluno.setParentesco(rs.getResponsavel().getParentesco());
				
				//salvar na base de dados o model ResponsavelAluno
				responsavelAlunos.saveAndFlush(responsavelAluno);
			}
		}
		
		tabelasResponsaveisSession.excluirTodosOsResponsaveis(uuid);
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
