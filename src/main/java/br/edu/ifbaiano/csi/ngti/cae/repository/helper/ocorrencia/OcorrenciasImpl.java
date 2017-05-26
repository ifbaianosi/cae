package br.edu.ifbaiano.csi.ngti.cae.repository.helper.ocorrencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.edu.ifbaiano.csi.ngti.cae.dto.OcorrenciaDTO;
import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;

public class OcorrenciasImpl implements OcorrenciasQueries{

	@PersistenceContext
	private EntityManager manager;
	
	
	
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

}
