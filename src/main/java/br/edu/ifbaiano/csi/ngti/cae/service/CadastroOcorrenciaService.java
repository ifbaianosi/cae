package br.edu.ifbaiano.csi.ngti.cae.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifbaiano.csi.ngti.cae.model.Ocorrencia;
import br.edu.ifbaiano.csi.ngti.cae.repository.Ocorrencias;

@Service
public class CadastroOcorrenciaService {

	@Autowired
	private Ocorrencias ocorrencias;
	
	@Transactional
	public void salvar(Ocorrencia ocorrencia){
		ocorrencias.save(ocorrencia);
	}
}
