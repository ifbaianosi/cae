package br.edu.ifbaiano.csi.ngti.cae.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifbaiano.csi.ngti.cae.model.Responsavel;
import br.edu.ifbaiano.csi.ngti.cae.model.Usuario;
import br.edu.ifbaiano.csi.ngti.cae.repository.Responsaveis;
import br.edu.ifbaiano.csi.ngti.cae.repository.ResponsavelAlunos;
import br.edu.ifbaiano.csi.ngti.cae.service.exception.EmailUsuarioJaCadastradoException;
import br.edu.ifbaiano.csi.ngti.cae.service.exception.ResponsavelJaCadastradoException;

@Service
public class CadastroResponsavelService {
	
	@Autowired
	private Responsaveis responsaveis;
	
	@Autowired
	private ResponsavelAlunos responsavelAlunos;

	@Transactional
	public Responsavel salvar(Responsavel responsavel){
		Optional<Responsavel> responsavelExistente = responsaveis.findByNomeIgnoreCaseAndContato(responsavel.getNome(), responsavel.getContato());
		if (responsavelExistente.isPresent() && !responsavelExistente.get().equals(responsavel)) {
			throw new ResponsavelJaCadastradoException("Já existe um responsável cadastrado com o nome e contato informado.");
		}
		
		return responsaveis.save(responsavel);
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
