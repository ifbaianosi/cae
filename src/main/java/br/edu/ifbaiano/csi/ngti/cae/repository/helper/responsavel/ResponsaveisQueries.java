package br.edu.ifbaiano.csi.ngti.cae.repository.helper.responsavel;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.edu.ifbaiano.csi.ngti.cae.model.Responsavel;
import br.edu.ifbaiano.csi.ngti.cae.repository.filter.ResponsavelFilter;

public interface ResponsaveisQueries {

	//TODO: mudar tipo do retorno Responsavel para ResponsavelDTO
	public Page<Responsavel> filtrar(ResponsavelFilter ocorrenciaFilter, Pageable pageable);
	public Optional<Responsavel> porCodigo(Long codigo);
}
