package br.edu.ifbaiano.csi.ngti.cae.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifbaiano.csi.ngti.cae.model.Ocorrencia;
import br.edu.ifbaiano.csi.ngti.cae.repository.Alunos;
import br.edu.ifbaiano.csi.ngti.cae.service.CadastroOcorrenciaService;

@Controller
@RequestMapping("/ocorrencias")
public class OcorrenciasController {
	
	@Autowired
	private CadastroOcorrenciaService cadastroOcorrenciaService;
	
	@Autowired
	private Alunos alunos;

	@GetMapping(value="/nova")
	public ModelAndView nova(Ocorrencia ocorrencia){
		
		return new ModelAndView("ocorrencia/CadastroOcorrencia"); 
	}
	
	@GetMapping(value="/formulario/{codigoaluno}")
	public ModelAndView formulario(@PathVariable("codigoaluno") Long codigoaluno){
		ModelAndView mv = new ModelAndView("ocorrencia/Formulario");
		
		Ocorrencia ocorrencia = new Ocorrencia();
		ocorrencia.setAluno(alunos.findOne(codigoaluno));
		
		mv.addObject("ocorrencia", ocorrencia);
		
		System.out.println("codigo aluno: "+ocorrencia.getAluno().getCodigo());
		
		return mv; 
	}
	
	@PostMapping(value={ "/salvar", "{\\d+}" }, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView salvarViaAjax(@RequestBody @Valid Ocorrencia ocorrencia, BindingResult result, RedirectAttributes attributs){
		System.out.println("salvarviaajax==========");
		if(result.hasErrors())
			return formulario(ocorrencia.getAluno().getCodigo());
		
		cadastroOcorrenciaService.salvar(ocorrencia);
			
		attributs.addFlashAttribute("mensagemSucesso", "Ocorrencia salva com sucesso");
		
		return new ModelAndView("redirect:/ocorrencias/nova");
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
