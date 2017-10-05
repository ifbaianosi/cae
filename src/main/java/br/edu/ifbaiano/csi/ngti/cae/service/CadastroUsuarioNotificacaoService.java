package br.edu.ifbaiano.csi.ngti.cae.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifbaiano.csi.ngti.cae.model.UsuarioNotificacao;
import br.edu.ifbaiano.csi.ngti.cae.repository.UsuarioNotificacoes;

@Service
public class CadastroUsuarioNotificacaoService {

	@Autowired
	private UsuarioNotificacoes usuarioNotificacoes;
	
	@Transactional
	public void salvar(UsuarioNotificacao usuarioNotificacao) {
		
		usuarioNotificacoes.save(usuarioNotificacao);
	}

}
