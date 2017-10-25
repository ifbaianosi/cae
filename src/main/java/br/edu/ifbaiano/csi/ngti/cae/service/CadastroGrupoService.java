package br.edu.ifbaiano.csi.ngti.cae.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifbaiano.csi.ngti.cae.model.Grupo;
import br.edu.ifbaiano.csi.ngti.cae.repository.Grupos;
import br.edu.ifbaiano.csi.ngti.cae.service.exception.GrupoJaCadastradoException;

@Service
public class CadastroGrupoService {
	
	@Autowired
	private Grupos grupos;

	@Transactional
	public void salvar(Grupo grupo){
		Optional<Grupo> grupoOptional = grupos.findByNomeIgnoreCase(grupo.getNome());
		if(grupoOptional.isPresent() && !grupoOptional.get().equals(grupo)){
			throw new GrupoJaCadastradoException("Já existe um grupo com o nome informado.");
		}
		
		grupos.save(grupo);
	}
}
