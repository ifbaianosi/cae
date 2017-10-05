package br.edu.ifbaiano.csi.ngti.cae.repository.helper.notificacao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import br.edu.ifbaiano.csi.ngti.cae.dto.NotificacaoDTO;
import br.edu.ifbaiano.csi.ngti.cae.model.TipoNotificacao;

public class NotificacoesImpl implements NotificacoesQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@Transactional(readOnly = true)
	@Override
	public List<NotificacaoDTO> findByCodigoNotIn(List<Long> codigos) {
		String jpql = "SELECT  new br.edu.ifbaiano.csi.ngti.cae.dto.NotificacaoDTO(codigo, descricao, data, uri, tipoNotificacao) "
				+ "FROM Notificacao n "
				+ "WHERE n.codigo NOT IN (:codigos) "
				+ "ORDER BY n.data DESC";
		
		return manager.createQuery(jpql, NotificacaoDTO.class)
				.setParameter("codigos", codigos)
				.getResultList();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<NotificacaoDTO> findByCodigoNotInAndTipo(List<Long> codigos) {
		String jpql = "SELECT  new br.edu.ifbaiano.csi.ngti.cae.dto.NotificacaoDTO(codigo, descricao, data, uri, tipoNotificacao) "
				+ "FROM Notificacao n "
				+ "WHERE n.codigo NOT IN (:codigos) "
				+ "AND n.tipo = 'NOVO_ENCAMINHAMENTO' "
				+ "ORDER BY n.data DESC";
		
		return manager.createQuery(jpql, NotificacaoDTO.class)
				.setParameter("codigos", codigos)
				.getResultList();
	}

	@Override
	public List<NotificacaoDTO> all() {
		String jpql = "SELECT  new br.edu.ifbaiano.csi.ngti.cae.dto.NotificacaoDTO(codigo, descricao, data, uri, tipoNotificacao) "
				+ "FROM Notificacao n "
				+ "ORDER BY n.data DESC";
		
		return manager.createQuery(jpql, NotificacaoDTO.class)
				.getResultList();
	}

	@Override
	public List<NotificacaoDTO> findByTipo(TipoNotificacao tipo) {
		String jpql = "SELECT  new br.edu.ifbaiano.csi.ngti.cae.dto.NotificacaoDTO(codigo, descricao, data, uri, tipoNotificacao) "
				+ "FROM Notificacao n "
				+ "WHERE n.tipoNotificacao = :tipo "
				+ "ORDER BY n.data DESC";
		
		return manager.createQuery(jpql, NotificacaoDTO.class)
				.setParameter("tipo", tipo)
				.getResultList();
	}

	@Override
	public Long quantidadeNotificacoes(List<Long> codigos) {//sem uso
		String jpql = "SELECT COUNT(n.codigo) AS notificacoes "
				+ "FROM Notificacao n "
				+ "WHERE n.codigo NOT IN (:codigos)";
		
		return manager.createQuery(jpql, Long.class)
				.setParameter("codigos", codigos)
				.getSingleResult();
	}
}
