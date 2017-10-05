package br.edu.ifbaiano.csi.ngti.cae.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.model.Notificacao;
import br.edu.ifbaiano.csi.ngti.cae.model.Ocorrencia;
import br.edu.ifbaiano.csi.ngti.cae.model.TipoCurso;
import br.edu.ifbaiano.csi.ngti.cae.model.TipoNotificacao;
import br.edu.ifbaiano.csi.ngti.cae.model.Usuario;
import br.edu.ifbaiano.csi.ngti.cae.model.UsuarioNotificacao;
import br.edu.ifbaiano.csi.ngti.cae.repository.Ocorrencias;
import br.edu.ifbaiano.csi.ngti.cae.session.ListaAlunosSession;

@Service
public class CadastroOcorrenciaService {
	
	@Autowired
	private CadastroNotificacaoService cadastroNotificacaoService;
	
	@Autowired
	private CadastroUsuarioNotificacaoService cadastroUsuarioNotificacaoService;
	
	@Autowired
	private Ocorrencias ocorrencias;
	
	@Autowired
	private ListaAlunosSession listaAlunosSession;
	
	@Transactional
	public void salvar(Ocorrencia ocorrencia, Usuario usuarioLogado){
		
		
		Ocorrencia o = null;
		if(ocorrencia.isNovo()){
			if(ocorrencia.getColetiva()){
				//salva uma unica ocorrencia para todos os alunos selecionados
				o = ocorrencias.save(ocorrencia);
				//Criar a notificação
				criarNotificacao(o, TipoNotificacao.NOVA_OCORRENCIA, usuarioLogado);
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
					
					o = ocorrencias.save(ocorr);
					//Criar a notificação
					criarNotificacao(o, TipoNotificacao.NOVA_OCORRENCIA, usuarioLogado);
				}
			}
			listaAlunosSession.excluirTodosOsAlunos(ocorrencia.getUuid());
		}else{//atualizar o registro da ocorrencia
			o = ocorrencias.save(ocorrencia);
			//Criar a notificação
			criarNotificacao(o, TipoNotificacao.ATUALIZACAO_OCORRENCIA, usuarioLogado);
		}
	}

	private void criarNotificacao(Ocorrencia ocorrencia, TipoNotificacao tipoNotificacao, Usuario usuario) {
		//Cria uma notificação para o registro da ocorrência
		Notificacao notificacao = new Notificacao();
		notificacao.setData(LocalDateTime.now());
		notificacao.setDescricao(ocorrencia.getDescricaoResumida());
		notificacao.setTipoNotificacao(tipoNotificacao);
		notificacao.setUri("ocorrencias/detalhes/"+ocorrencia.getCodigo());
		
		Notificacao notificacaoGravada = cadastroNotificacaoService.salvar(notificacao);
		
		//Marca a notificação criada como visualizada para o usuario logado
		UsuarioNotificacao usuarioNotificacao = new UsuarioNotificacao();
		usuarioNotificacao.setDataVisualizacao(LocalDateTime.now());
		usuarioNotificacao.setNotificacao(notificacaoGravada);
		usuarioNotificacao.setUsuario(usuario);
		
		//Marca a notificação como visualizada pelo usuario logado
		cadastroUsuarioNotificacaoService.salvar(usuarioNotificacao);
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
