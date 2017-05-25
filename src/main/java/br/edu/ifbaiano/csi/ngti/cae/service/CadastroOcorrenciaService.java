package br.edu.ifbaiano.csi.ngti.cae.service;

import java.util.ArrayList;
import java.util.List;

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

	@Transactional
	public void excluir(Long[] codigos) {
		List<Ocorrencia> ocorrenciasList = new ArrayList<Ocorrencia>();
		for(int i =0; i < codigos.length; i++){
			Ocorrencia ocr = new Ocorrencia();
			ocr.setCodigo(codigos[i]);
			ocorrenciasList.add(ocr);
		}
		ocorrencias.deleteInBatch(ocorrenciasList);
	}
}
