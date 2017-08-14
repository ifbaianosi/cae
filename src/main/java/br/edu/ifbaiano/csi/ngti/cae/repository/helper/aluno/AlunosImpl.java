package br.edu.ifbaiano.csi.ngti.cae.repository.helper.aluno;

import java.util.List;
import java.util.Optional;

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

import br.edu.ifbaiano.csi.ngti.cae.dto.AlunoDTO;
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
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Aluno> filtroAdicionarNaOcorrencia(AlunoFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Aluno.class);
		
		//ADICIONA O FILTRO A CRITERIA DO HIBERNATE
		adicionarFiltro(filtro, criteria);
		
		return criteria.list();
	}
	
	@Override
	public List<Aluno> porNomeOuMatricula(String nomeOuMatricula) {
		String jpql = "SELECT a "
				+ "FROM Aluno a "
				+ "WHERE lower(a.nome) like lower(:nomeOuMatricula) OR a.matricula like :nomeOuMatricula";
		
		List<Aluno> alunosFiltrados = manager.createQuery(jpql, Aluno.class)
											.setParameter("nomeOuMatricula", nomeOuMatricula + "%")
											.getResultList();

		return alunosFiltrados;
	}
	
	//NO MOMENTO NÃO ESTA SENDO USADO ESSE METODO, FOI USADO O PROPRIO METODO DO SPRING DATA JPA findByMatricula(matricula)
	@Override
	public Optional<AlunoDTO> porMatricula(String matricula) {
		String jpql = "SELECT new br.edu.ifbaiano.csi.ngti.cae.dto.AlunoDTO(codigo, nome, matricula, serieTurma, sexo) "
				+ "FROM Aluno a "
				+ "WHERE a.matricula like :matricula";
		
		Optional<AlunoDTO> alunoFiltradoOptional = manager.createQuery(jpql, AlunoDTO.class)
											.setParameter("matricula", matricula)
											.getResultList().stream().findFirst();

		return alunoFiltradoOptional;
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
				criteria.add(Restrictions.like("matricula", filtro.getMatricula(), MatchMode.START));
			
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
			if(filtro.getRegime() != null)
				criteria.add(Restrictions.eq("regime", filtro.getRegime()));
			
			//FILTRO CURSO
			if(filtro.getCurso() != null && filtro.getCurso().getCodigo() != null)
				criteria.add(Restrictions.eq("curso", filtro.getCurso()));
			
			//FILTRO SERIE/TURMA
			if(filtro.getSerieTurma() != null)
				criteria.add(Restrictions.eq("serieTurma", filtro.getSerieTurma()));
			
			//FILTRO ALOJAMENTO
			if(filtro.getAlojamento() != null)
				criteria.add(Restrictions.eq("alojamento", filtro.getAlojamento()));
			
			//FILTRO APARTAMENTO
			if(filtro.getApartamento() != null)
				criteria.add(Restrictions.eq("apartamento", filtro.getApartamento()));
			
			//FILTRO NOMESOCIAL
			if(!StringUtils.isEmpty(filtro.getNomeSocial()))
				criteria.add(Restrictions.ilike("nomeSocial", filtro.getNomeSocial(), MatchMode.START));
			
			//FILTRO STATUS
			if(filtro.getStatus() != null)
				criteria.add(Restrictions.eq("status", filtro.getStatus()));
			
			//FILTRO PERMISSÃO DE SAIDA DO CAMPUS
			if(filtro.getSaida() != null && filtro.getSaida() == true)
				criteria.add(Restrictions.eq("saida", filtro.getSaida()));
		}
	}

	private Long total(AlunoFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Aluno.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long)criteria.uniqueResult();
	}
	
	/*private boolean filtrarPorSaida(AlunoFilter filtro){
		return filtro.getSaida() == true || filtro.getSaida() == false
	}*/
	
}
