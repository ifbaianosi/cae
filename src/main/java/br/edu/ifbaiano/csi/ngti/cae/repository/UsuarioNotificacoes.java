package br.edu.ifbaiano.csi.ngti.cae.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifbaiano.csi.ngti.cae.model.UsuarioNotificacao;
import br.edu.ifbaiano.csi.ngti.cae.repository.helper.usuarioNotificacoes.UsuarioNotificacoesQueries;

public interface UsuarioNotificacoes extends JpaRepository<UsuarioNotificacao, Long>, UsuarioNotificacoesQueries{
	

}
