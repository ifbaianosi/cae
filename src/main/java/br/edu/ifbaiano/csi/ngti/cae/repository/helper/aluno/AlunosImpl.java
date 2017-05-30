package br.edu.ifbaiano.csi.ngti.cae.repository.helper.aluno;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.repository.filter.AlunoFilter;
import br.edu.ifbaiano.csi.ngti.cae.repository.paginacao.PaginacaoUtil;

public class AlunosImpl implements AlunosQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Aluno> filtrar(AlunoFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Aluno.class);
		
		//SETA OS PARAMETROS DE TOTAL DE REGISTROS POR PAGINA E PRIMEIRO REGISTRO DA PAGINA
		paginacaoUtil.preparar(criteria, pageable);
		
		//ADICIONA O FILTRO A CRITERIA DO HIBERNATE
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}


	/**
	 * ADICIONA UM FILTRO A PESQUISA 
	 * 
	 * @param filtro
	 * @param pageable
	 */
	private void adicionarFiltro(AlunoFilter filtro, Criteria criteria) {
		if(filtro != null){
			//FILTRO MATRICULA
			if(!StringUtils.isEmpty(filtro.getMatricula()))//se o filtro matricula não estiver vazio
				criteria.add(Restrictions.ilike("matricula", filtro.getMatricula(), MatchMode.START));
			
			//FILTRO NOME
			if(!StringUtils.isEmpty(filtro.getNome()))
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.START));
			
			//FILTRO SEXO
			if(filtro.getSexo() != null)
				criteria.add(Restrictions.eq("sexo", filtro.getSexo()));
			
			//FILTRO DATA NASCIMENTO
			if(filtro.getDataNascimento() != null)
				criteria.add(Restrictions.eq("dataNascimento", filtro.getDataNascimento()));
			
			//FILTRO IDENTIFICACAO
			if(filtro.getIdentificacao() != null)
				criteria.add(Restrictions.eq("identificacao", filtro.getIdentificacao()));
			
			//FILTRO CURSO
			if(filtro.getCurso() != null && filtro.getCurso().getCodigo() != null)
				criteria.add(Restrictions.eq("curso", filtro.getCurso()));
			
			//FILTRO SERIE/TURMA
			if(filtro.getSerieTurma() != null)
				criteria.add(Restrictions.eq("serieTurma", filtro.getSerieTurma()));
		}
	}

	private Long total(AlunoFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Aluno.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long)criteria.uniqueResult();
	}
}
