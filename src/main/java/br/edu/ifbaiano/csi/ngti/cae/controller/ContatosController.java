package br.edu.ifbaiano.csi.ngti.cae.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.edu.ifbaiano.csi.ngti.cae.model.Contato;
import br.edu.ifbaiano.csi.ngti.cae.repository.Contatos;
import br.edu.ifbaiano.csi.ngti.cae.service.CadastroContatoService;
import br.edu.ifbaiano.csi.ngti.cae.service.exception.ContatoJaCadastradoException;

@Controller
@RequestMapping("/contatos")
public class ContatosController {
	
	@Autowired
	private CadastroContatoService cadastroContatoService;
	
	@Autowired
	private Contatos contatos;

	@PostMapping(value="/novo", consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> cadastroRapido(@RequestBody @Valid Contato contato, BindingResult result){
		
		if(result.hasErrors())
			return ResponseEntity.badRequest().build();
		
		try {
			cadastroContatoService.salvar(contato);
		} catch (ContatoJaCadastradoException e) {
			contato = contatos.findByNumero(contato.getNumero()).get();
			System.out.println("codigo: "+ contato.getCodigo());
			return ResponseEntity.ok(contato);
		}
		
		return ResponseEntity.ok(contato);
	}
	
	@PutMapping(value="/iswhatsapp")
	public @ResponseBody ResponseEntity<?> isWhatsapp(@RequestParam("codigo") Long codigo,@RequestParam("whatsapp") Boolean whatsapp){
		cadastroContatoService.alterarWhatsapp(codigo, whatsapp);
		
		return ResponseEntity.ok().build();
	}
}
