package br.edu.ifbaiano.csi.ngti.cae.repository.paginacao;

import org.hibernate.Criteria;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PaginacaoUtil {

	public void preparar(Criteria criteria, Pageable pageable){
		//PREPARAR PAGINACAO
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		criteria.setFirstResult(primeiroRegistroDaPagina);
		criteria.setMaxResults(totalRegistrosPorPagina);
		
		//PREPARAR ORDENAÇÃO
		
	}
}
