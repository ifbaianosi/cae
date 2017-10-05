package br.edu.ifbaiano.csi.ngti.cae.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifbaiano.csi.ngti.cae.model.Notificacao;
import br.edu.ifbaiano.csi.ngti.cae.repository.Notificacoes;

@Service
public class CadastroNotificacaoService {

	@Autowired
	private Notificacoes notificacoes;
	
	@Transactional
	public Notificacao salvar(Notificacao notificacao) {
		
		return notificacoes.save(notificacao);
	}

}
