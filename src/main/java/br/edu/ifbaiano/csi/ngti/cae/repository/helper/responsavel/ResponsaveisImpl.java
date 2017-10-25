package br.edu.ifbaiano.csi.ngti.cae.repository.helper.responsavel;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.edu.ifbaiano.csi.ngti.cae.model.Ocorrencia;
import br.edu.ifbaiano.csi.ngti.cae.model.Responsavel;
import br.edu.ifbaiano.csi.ngti.cae.repository.filter.ResponsavelFilter;
import br.edu.ifbaiano.csi.ngti.cae.repository.paginacao.PaginacaoUtil;

public class ResponsaveisImpl implements ResponsaveisQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Responsavel> filtrar(ResponsavelFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Responsavel.class);
		
		//SETA OS PARAMETROS DE TOTAL DE REGISTROS POR PAGINA E PRIMEIRO REGISTRO DA PAGINA
		paginacaoUtil.preparar(criteria, pageable);
		
		//SETA ORDENACAO
		criteria.addOrder(Order.asc("codigo"));
		
		//ADICIONA O FILTRO A CRITERIA DO HIBERNATE
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public Optional<Responsavel> porCodigo(Long codigo) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Responsavel.class);
		criteria.createAlias("responsavelAlunos", "ra", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("codigo", codigo));
		Optional<Responsavel> responsavelOptional = (Optional<Responsavel>) criteria.list().stream().findFirst();;
		return responsavelOptional;
	}

	/**
	 * ADICIONA UM FILTRO A PESQUISA 
	 * 
	 * @param filtro
	 * @param pageable
	 */
	private void adicionarFiltro(ResponsavelFilter filtro, Criteria criteria) {
		if(filtro != null){
			
			//FILTRO NOME
			if(!StringUtils.isEmpty(filtro.getNome()))
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			
			//FILTRO CONTATO
			if(!StringUtils.isEmpty(filtro.getContato()))
				criteria.add(Restrictions.ilike("contato", filtro.getContato(), MatchMode.START));

		}
	}

	private Long total(ResponsavelFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Responsavel.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long)criteria.uniqueResult();
	}
}
