package br.edu.ifbaiano.csi.ngti.cae.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifbaiano.csi.ngti.cae.model.Responsavel;
import br.edu.ifbaiano.csi.ngti.cae.repository.Responsaveis;
import br.edu.ifbaiano.csi.ngti.cae.repository.ResponsavelAlunos;

@Service
public class CadastroResponsavelService {
	
	@Autowired
	private Responsaveis responsaveis;
	
	@Autowired
	private ResponsavelAlunos responsavelAlunos;

	@Transactional
	public void salvar(Responsavel responsavel){
		if(responsavel.isNovo()){
			Optional<Responsavel> responsavelOptional = responsaveis.findByNomeIgnoreCaseAndContato(responsavel.getNome(), responsavel.getContato());
			if(!responsavelOptional.isPresent())
				responsaveis.saveAndFlush(responsavel);
		}
	}

	/*
	 * Excluir responsaval do aluno na tabela associativa (responsavel_aluno)
	 */
	@Transactional
	public void excluir(Long codigo) {
		responsavelAlunos.delete(codigo);
	}

	/*@Transactional
	public void alterarWhatsapp(Long codigo, Boolean whatsapp) {
		responsaveis.findOne(codigo).setWhatsapp(whatsapp);
	}
	
	public void salvarResponsaveis(List<ResponsavelSession> responsaveisDaSession){
		System.out.println("SalvarResponsaveis: "+responsaveisDaSession.size());
	}*/
}
