package br.edu.ifbaiano.csi.ngti.cae.repository.helper.ocorrencia;

import java.util.List;

import br.edu.ifbaiano.csi.ngti.cae.dto.OcorrenciaDTO;
import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;

public interface OcorrenciasQueries {

	public List<OcorrenciaDTO> porAluno(Aluno aluno);
	public OcorrenciaDTO porCodigo(Long codigo);
}
