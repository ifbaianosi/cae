package br.edu.ifbaiano.csi.ngti.cae.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.edu.ifbaiano.csi.ngti.cae.model.Contato;
import br.edu.ifbaiano.csi.ngti.cae.model.Responsavel;
import br.edu.ifbaiano.csi.ngti.cae.repository.Contatos;
import br.edu.ifbaiano.csi.ngti.cae.service.CadastroContatoService;
import br.edu.ifbaiano.csi.ngti.cae.service.exception.ContatoJaCadastradoException;
import br.edu.ifbaiano.csi.ngti.cae.session.TabelasResponsaveisSession;

@Controller
@RequestMapping("/responsaveis")
public class ResponsaveisController {
	
	@Autowired
	private CadastroContatoService cadastroContatoService;
	
	@Autowired
	private Contatos responsavels;
	
	@Autowired
	private TabelasResponsaveisSession tabelasResponsaveisSession;
	
	@PostMapping(value="/adicionar", consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> adicionarResponsavel(@RequestBody @Valid Responsavel responsavel, BindingResult result){
		
		if(result.hasErrors()){System.out.println("não passou pela validação...");
			return ResponseEntity.badRequest().build();}
		
		List<Responsavel> responsaveis = new ArrayList<>();
		responsaveis.add(responsavel);
		
		tabelasResponsaveisSession.adicionarResponsavel(responsavel.getUuid(), responsaveis);
		
		return ResponseEntity.ok(tabelasResponsaveisSession.getResponsaveis(responsavel.getUuid()));
	}
	
	@DeleteMapping(value="/remover-todos")
	public ResponseEntity<?> removerTodosResponsaveis(@RequestBody String uuid){
		tabelasResponsaveisSession.excluirTodosOsResponsavels(uuid);
		
		return ResponseEntity.ok(tabelasResponsaveisSession.getResponsaveis(uuid));
	} 
	
	@DeleteMapping(value="/remover/{uuid}/{contato}")
	public ResponseEntity<?> removerResponsavel(@PathVariable("uuid") String uuid, @PathVariable("contato") String contato){
		Responsavel responsavel = new Responsavel();
		responsavel.setContato(contato);
		tabelasResponsaveisSession.excluirResponsavel(uuid, responsavel);
		
		return ResponseEntity.ok(tabelasResponsaveisSession.getResponsaveis(uuid));
	} 

	@PostMapping(value="/novo", consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> cadastroRapido(@RequestBody @Valid Contato responsavel, BindingResult result){
		
		if(result.hasErrors())
			return ResponseEntity.badRequest().build();
		
		try {
			cadastroContatoService.salvar(responsavel);
		} catch (ContatoJaCadastradoException e) {
			responsavel = responsavels.findByNumero(responsavel.getNumero()).get();
			System.out.println("codigo: "+ responsavel.getCodigo());
			return ResponseEntity.ok(responsavel);
		}
		
		return ResponseEntity.ok(responsavel);
	}
	
	@PutMapping(value="/iswhatsapp")
	public @ResponseBody ResponseEntity<?> isWhatsapp(@RequestParam("codigo") Long codigo,@RequestParam("whatsapp") Boolean whatsapp){
		cadastroContatoService.alterarWhatsapp(codigo, whatsapp);
		
		return ResponseEntity.ok().build();
	}
}
