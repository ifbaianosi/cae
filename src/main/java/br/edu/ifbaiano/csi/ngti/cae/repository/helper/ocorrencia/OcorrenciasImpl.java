package br.edu.ifbaiano.csi.ngti.cae.repository.helper.ocorrencia;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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

import br.edu.ifbaiano.csi.ngti.cae.dto.OcorrenciaDTO;
import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.model.Ocorrencia;
import br.edu.ifbaiano.csi.ngti.cae.repository.filter.OcorrenciaFilter;
import br.edu.ifbaiano.csi.ngti.cae.repository.paginacao.PaginacaoUtil;

public class OcorrenciasImpl implements OcorrenciasQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	


	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Ocorrencia> filtrar(OcorrenciaFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Ocorrencia.class);
		
		//SETA OS PARAMETROS DE TOTAL DE REGISTROS POR PAGINA E PRIMEIRO REGISTRO DA PAGINA
		paginacaoUtil.preparar(criteria, pageable);
		
		//SETA ORDENACAO
		criteria.addOrder(Order.desc("dataOcorrido"));
		
		//ADICIONA O FILTRO A CRITERIA DO HIBERNATE
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Ocorrencia> buscarComEncaminhamentos(Aluno aluno) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Ocorrencia.class);
		criteria.createAlias("encaminhamentos", "e", JoinType.INNER_JOIN);
		/*criteria.add(Restrictions.eq("aluno", aluno));
		criteria.addOrder(Order.desc("dataRegistro"));*/
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Ocorrencia>)criteria.list();
	}
	
	@Override
	public List<OcorrenciaDTO> porAluno(Aluno aluno) {
		String jpql = "SELECT new br.edu.ifbaiano.csi.ngti.cae.dto.OcorrenciaDTO(codigo, dataRegistro, dataOcorrido, local, descricao) "
						+ "FROM Ocorrencia o "
						+ "WHERE o.aluno = :aluno "
						+ "ORDER BY o.dataRegistro DESC";
		
		return manager.createQuery(jpql, OcorrenciaDTO.class)
				.setParameter("aluno", aluno)
				.getResultList();
	}



	@Override
	public OcorrenciaDTO porCodigo(Long codigo) {
		String jpql = "SELECT new br.edu.ifbaiano.csi.ngti.cae.dto.OcorrenciaDTO(codigo, dataRegistro, dataOcorrido, local, descricao) "
				+ "FROM Ocorrencia o "
				+ "WHERE o.codigo = :codigo";

		return manager.createQuery(jpql, OcorrenciaDTO.class)
				.setParameter("codigo", codigo)
				.getSingleResult();
	}


	/**
	 * ADICIONA UM FILTRO A PESQUISA 
	 * 
	 * @param filtro
	 * @param pageable
	 */
	private void adicionarFiltro(OcorrenciaFilter filtro, Criteria criteria) {
		if(filtro != null){
			//FILTRO LOCAL
			if(!StringUtils.isEmpty(filtro.getLocal()))
				criteria.add(Restrictions.ilike("local", filtro.getLocal(), MatchMode.ANYWHERE));
			
			//FILTRO DATA OCORRIDO
			if(filtro.getDataOcorrido() != null){
				LocalDateTime desde = LocalDateTime.of(filtro.getDataOcorrido(), LocalTime.of(0, 0));
				criteria.add(Restrictions.ge("dataOcorrido", desde));
			}
			
			//FILTRO DATA OCORRIDO ATE
			if (filtro.getDataOcorridoAte() != null) {
				LocalDateTime ate = LocalDateTime.of(filtro.getDataOcorridoAte(), LocalTime.of(23, 59));
				criteria.add(Restrictions.le("dataOcorrido", ate));
			}
			
			//FILTRO ALUNO
			if(filtro.getAluno() != null && filtro.getAluno().getCodigo() != null)
				criteria.add(Restrictions.eq("aluno", filtro.getAluno()));
		}
	}

	private Long total(OcorrenciaFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Ocorrencia.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long)criteria.uniqueResult();
	}


}
