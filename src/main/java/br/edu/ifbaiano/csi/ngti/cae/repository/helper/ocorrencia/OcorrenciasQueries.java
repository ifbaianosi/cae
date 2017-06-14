package br.edu.ifbaiano.csi.ngti.cae.repository.helper.ocorrencia;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.edu.ifbaiano.csi.ngti.cae.dto.OcorrenciaDTO;
import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.model.Ocorrencia;
import br.edu.ifbaiano.csi.ngti.cae.repository.filter.OcorrenciaFilter;

public interface OcorrenciasQueries {

	public List<OcorrenciaDTO> porAluno(Aluno aluno);
	public OcorrenciaDTO porCodigo(Long codigo);
	public Page<Ocorrencia> filtrar(OcorrenciaFilter ocorrenciaFilter, Pageable pageable);
	public List<Ocorrencia> buscarComEncaminhamentos(Aluno aluno);
}
