package br.edu.ifbaiano.csi.ngti.cae.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifbaiano.csi.ngti.cae.model.Encaminhamento;
import br.edu.ifbaiano.csi.ngti.cae.repository.Encaminhamentos;

@Service
public class CadastroEncaminhamentoService {
	
	@Autowired
	private Encaminhamentos encaminhamentos;

	@Transactional
	public void salvar(Encaminhamento encaminhamento){
		encaminhamentos.save(encaminhamento);
	}

}
