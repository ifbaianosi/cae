package br.edu.ifbaiano.csi.ngti.cae.repository.helper.grupo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.edu.ifbaiano.csi.ngti.cae.model.Grupo;
import br.edu.ifbaiano.csi.ngti.cae.repository.filter.GrupoFilter;
import br.edu.ifbaiano.csi.ngti.cae.repository.paginacao.PaginacaoUtil;

public class GruposImpl implements GruposQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Grupo> filtrar(GrupoFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Grupo.class);
		
		//SETA OS PARAMETROS DE TOTAL DE REGISTROS POR PAGINA E PRIMEIRO REGISTRO DA PAGINA
		paginacaoUtil.preparar(criteria, pageable);
		
		//ADICIONA O FILTRO A CRITERIA DO HIBERNATE
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}
	
	@Transactional(readOnly = true)
	@Override
	public Grupo porCodigoComPermissoes(Long codigo) {
		System.out.println("====> porCodigoComPermissoes");
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Grupo.class);
		criteria.createAlias("permissoes", "p", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("codigo", codigo));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		/*criteria.addOrder(Order.desc("codigo"));*/
		return (Grupo) criteria.uniqueResult();
	}
	
	/**
	 * ADICIONA UM FILTRO A PESQUISA 
	 * 
	 * @param filtro
	 * @param pageable
	 */
	private void adicionarFiltro(GrupoFilter filtro, Criteria criteria) {
		if(filtro != null){
			//FILTRO NOME
			if(!StringUtils.isEmpty(filtro.getNome()))
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.START));
		}
	}

	private Long total(GrupoFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Grupo.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long)criteria.uniqueResult();
	}

}
