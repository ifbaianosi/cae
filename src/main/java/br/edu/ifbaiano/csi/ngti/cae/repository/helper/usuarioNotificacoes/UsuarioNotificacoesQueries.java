package br.edu.ifbaiano.csi.ngti.cae.repository.helper.usuarioNotificacoes;

import java.util.List;

import br.edu.ifbaiano.csi.ngti.cae.model.Usuario;

public interface UsuarioNotificacoesQueries {

	public List<Long> findByUsuario(Usuario usuario); 
}
