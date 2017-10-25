package br.edu.ifbaiano.csi.ngti.cae.repository.helper.grupo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.edu.ifbaiano.csi.ngti.cae.model.Grupo;
import br.edu.ifbaiano.csi.ngti.cae.repository.filter.GrupoFilter;

public interface GruposQueries {

	public Page<Grupo> filtrar(GrupoFilter usuarioFilter, Pageable pageable);
	public Grupo porCodigoComPermissoes(Long codigo);
}
