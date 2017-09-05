package br.edu.ifbaiano.csi.ngti.cae.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.model.Ocorrencia;
import br.edu.ifbaiano.csi.ngti.cae.model.TipoCurso;
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
			if(ocorrencia.getColetiva()){
				//salva uma unica ocorrencia para todos os alunos selecionados
				ocorrencias.save(ocorrencia);
			} else {
				//salva uma ocorrencia para cada um dos alunos selecionados
				List<Aluno> alunosList = listaAlunosSession.getAlunos(ocorrencia.getUuid());
				System.out.println("--------------------------> alunos selecionados: "+ocorrencia.getAlunos().size());
				
				for(Aluno aluno: alunosList){
					Ocorrencia ocorr = new Ocorrencia();
					
					ocorr.setAluno(aluno);
					ocorr.setLocal(ocorrencia.getLocal());
					ocorr.setDescricao(ocorrencia.getDescricao());
					ocorr.setDataOcorrido(ocorrencia.getDataOcorrido());
					ocorr.setUsuario(ocorrencia.getUsuario());
					System.out.println("codigo do aluno: --> "+aluno.getCodigo());
					List<Aluno> als = new ArrayList<>();
					als.add(aluno);
					ocorr.setAlunos(als);
					/*ocorr.setAlunos(ocorrencia.getAlunos());*/
					ocorr.setRegime(aluno.getRegime().getDescricao());
					if(aluno.getCurso().getTipoCurso() != TipoCurso.SUPERIOR)
						ocorr.setSerie(aluno.getSerieTurma().getDescricao());
					
					ocorrencias.save(ocorr);
				}
			}
			listaAlunosSession.excluirTodosOsAlunos(ocorrencia.getUuid());
		}else{
			//atualizar o registro da ocorrencia
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
