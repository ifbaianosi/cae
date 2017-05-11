package br.edu.ifbaiano.csi.ngti.cae.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifbaiano.csi.ngti.cae.model.Contato;
import br.edu.ifbaiano.csi.ngti.cae.repository.Contatos;
import br.edu.ifbaiano.csi.ngti.cae.service.exception.ContatoJaCadastradoException;

@Service
public class CadastroContatoService {
	
	@Autowired
	private Contatos contatos;

	@Transactional
	public void salvar(Contato contato){
		if(contato.isNovo()){
			Optional<Contato> contatoOptional = contatos.findByNumero(contato.getNumero());
			if(contatoOptional.isPresent())
				throw new ContatoJaCadastradoException("Contato já existente");
		}
			
		contatos.save(contato);
	}

	@Transactional
	public void alterarWhatsapp(Long codigo, Boolean whatsapp) {
		contatos.findOne(codigo).setWhatsapp(whatsapp);
	}
}
