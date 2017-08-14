package br.edu.ifbaiano.csi.ngti.cae.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.model.Ocorrencia;
import br.edu.ifbaiano.csi.ngti.cae.repository.Ocorrencias;
import br.edu.ifbaiano.csi.ngti.cae.session.ListaAlunosSession;

@Service
public class CadastroOcorrenciaService {

	@Autowired
	private Ocorrencias ocorrencias;
	
	@Autowired
	private ListaAlunosSession listaAlunosSession;
	
	@Transactional
	public void salvar(Ocorrencia ocorrencia){
		
		if(ocorrencia.isNovo()){
			List<Aluno> alunosList = listaAlunosSession.getAlunos(ocorrencia.getUuid());
			ocorrencia.setAlunos(alunosList);
			
			for(Aluno aluno: alunosList){
				Ocorrencia ocorr = new Ocorrencia();
				
				ocorr.setAluno(aluno);
				ocorr.setLocal(ocorrencia.getLocal());
				ocorr.setDescricao(ocorrencia.getDescricao());
				ocorr.setDataOcorrido(ocorrencia.getDataOcorrido());
				ocorr.setUsuario(ocorrencia.getUsuario());
				ocorr.setAlunos(ocorrencia.getAlunos());
				
				ocorrencias.save(ocorr);
			}
			listaAlunosSession.excluirTodosOsAlunos(ocorrencia.getUuid());
			
		}else{
			ocorrencias.save(ocorrencia);
		}
	}
	
	@Transactional
	public void salvarAjax(Ocorrencia ocorrencia){
			ocorrencias.save(ocorrencia);
	}

	@Transactional
	public void excluir(Long[] codigos) {
		List<Ocorrencia> ocorrenciasList = new ArrayList<Ocorrencia>();
		for(int i =0; i < codigos.length; i++){
			Ocorrencia ocr = new Ocorrencia();
			ocr.setCodigo(codigos[i]);
			ocorrenciasList.add(ocr);
		}
		ocorrencias.deleteInBatch(ocorrenciasList);
	}
}
