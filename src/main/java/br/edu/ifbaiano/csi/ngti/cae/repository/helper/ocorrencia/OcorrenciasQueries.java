package br.edu.ifbaiano.csi.ngti.cae.repository.helper.ocorrencia;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.edu.ifbaiano.csi.ngti.cae.dto.OcorrenciaDTO;
import br.edu.ifbaiano.csi.ngti.cae.dto.OcorrenciasPorAluno;
import br.edu.ifbaiano.csi.ngti.cae.dto.OcorrenciasPorLocal;
import br.edu.ifbaiano.csi.ngti.cae.dto.GraficoOcorrenciasPorMes;
import br.edu.ifbaiano.csi.ngti.cae.dto.OcorrenciasPorUsuario;
import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.model.Ocorrencia;
import br.edu.ifbaiano.csi.ngti.cae.repository.filter.OcorrenciaFilter;

public interface OcorrenciasQueries {

	public List<OcorrenciaDTO> porAluno(Aluno aluno);
	public Ocorrencia porCodigo(Long codigo);
	public Page<Ocorrencia> filtrar(OcorrenciaFilter ocorrenciaFilter, Pageable pageable);
	public List<Ocorrencia> buscarComEncaminhamentos(Aluno aluno);
	public Ocorrencia buscarComEncaminhamentosPorCodigo(Long codigoOcorrencia);
	public List<String> getLocais(String local);
	public List<OcorrenciasPorLocal> totalOcorrenciasPorLocal();
	public List<OcorrenciasPorUsuario> totalOcorrenciasPorUsuario();
	public List<OcorrenciasPorAluno> totalOcorrenciasPorAluno();
	public Ocorrencia buscarComAlunos(Long codigo);
	public List<GraficoOcorrenciasPorMes> totalPorMes();
}
