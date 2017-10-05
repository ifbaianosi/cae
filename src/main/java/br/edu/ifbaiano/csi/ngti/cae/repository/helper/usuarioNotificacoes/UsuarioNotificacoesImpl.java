package br.edu.ifbaiano.csi.ngti.cae.repository.helper.usuarioNotificacoes;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import br.edu.ifbaiano.csi.ngti.cae.model.Usuario;

public class UsuarioNotificacoesImpl implements UsuarioNotificacoesQueries{

	@PersistenceContext
	private EntityManager manager;
	

	@Transactional(readOnly = true)
	@Override
	public List<Long> findByUsuario(Usuario usuario) {
		String jpql = "SELECT un.notificacao.codigo "
				+ "FROM UsuarioNotificacao un "
				+ "WHERE un.usuario = :usuario ";
		
		return manager.createQuery(jpql, Long.class)
				.setParameter("usuario", usuario)
				.getResultList();
	}
}
