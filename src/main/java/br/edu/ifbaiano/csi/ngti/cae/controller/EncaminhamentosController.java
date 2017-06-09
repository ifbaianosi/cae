package br.edu.ifbaiano.csi.ngti.cae.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifbaiano.csi.ngti.cae.model.Encaminhamento;
import br.edu.ifbaiano.csi.ngti.cae.repository.Encaminhamentos;
import br.edu.ifbaiano.csi.ngti.cae.service.CadastroEncaminhamentoService;
import br.edu.ifbaiano.csi.ngti.cae.service.CadastroOcorrenciaService;

@Controller
@RequestMapping("/encaminhamento")
public class EncaminhamentosController {
	
	@Autowired
	private CadastroEncaminhamentoService cadastroEncaminhamentoService;
	
	@PostMapping(value="/salvar")
	public ResponseEntity<?> salvarViaAjax(@Valid Encaminhamento encaminhamento, BindingResult result){
		
		System.out.println("encaminhamentos/salvar");
		System.out.println(encaminhamento.getOcorrencia().getCodigo());
		System.out.println(encaminhamento.getTipoEncaminhamento());
		
		if(result.hasErrors()){
			return ResponseEntity.badRequest().build();
		}
		
		cadastroEncaminhamentoService.salvar(encaminhamento);
		
		return ResponseEntity.ok().body(HttpStatus.CREATED);
	}
	
}
