package br.edu.ifbaiano.csi.ngti.cae.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifbaiano.csi.ngti.cae.model.Ocorrencia;
import br.edu.ifbaiano.csi.ngti.cae.service.CadastroOcorrenciaService;

@Controller
@RequestMapping("/ocorrencias")
public class OcorrenciasController {
	
	@Autowired
	private CadastroOcorrenciaService cadastroOcorrenciaService;

	@GetMapping(value="/nova")
	public ModelAndView nova(Ocorrencia ocorrencia){
		
		return new ModelAndView("ocorrencia/CadastroOcorrencia"); 
	}
	
	@PostMapping(value={ "/nova", "{\\d+}" })
	public ModelAndView salvar(@Valid Ocorrencia ocorrencia, BindingResult result, RedirectAttributes attributs){
		
		if(result.hasErrors())
			return nova(ocorrencia);
		
		cadastroOcorrenciaService.salvar(ocorrencia);
			
		attributs.addFlashAttribute("mensagemSucesso", "Ocorrencia salva com sucesso");
		
		return new ModelAndView("redirect:/ocorrencias/nova");
	}
}
