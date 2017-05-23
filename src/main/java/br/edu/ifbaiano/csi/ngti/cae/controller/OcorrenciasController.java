package br.edu.ifbaiano.csi.ngti.cae.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.model.Ocorrencia;
import br.edu.ifbaiano.csi.ngti.cae.repository.Alunos;
import br.edu.ifbaiano.csi.ngti.cae.repository.Ocorrencias;
import br.edu.ifbaiano.csi.ngti.cae.service.CadastroOcorrenciaService;

@Controller
@RequestMapping("/ocorrencias")
public class OcorrenciasController {
	
	@Autowired
	private CadastroOcorrenciaService cadastroOcorrenciaService;
	
	@Autowired
	private Alunos alunos;
	
	@Autowired
	private Ocorrencias ocorrencias;
	
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
	
	@PostMapping(value="/salvar")
	public ModelAndView salvarViaAjax(@Valid Ocorrencia ocorrencia, BindingResult result, RedirectAttributes attributs){
		ModelAndView mv = new ModelAndView("ocorrencia/Formulario");
		if(result.hasErrors()){
			mv.addObject("ocorrencia", ocorrencia);
			return mv;
		}
		
		cadastroOcorrenciaService.salvar(ocorrencia);
		
		return formulario(ocorrencia.getAluno().getCodigo());
	}
	
	@PostMapping(value={ "/nova", "{\\d+}" })
	public ModelAndView salvar(@Valid Ocorrencia ocorrencia, BindingResult result, RedirectAttributes attributs){
		
		if(result.hasErrors())
			return nova(ocorrencia);
		
		cadastroOcorrenciaService.salvar(ocorrencia);
			
		attributs.addFlashAttribute("mensagemSucesso", "Ocorrencia salva com sucesso");
		
		return new ModelAndView("redirect:/ocorrencias/nova");
	}
	
	@RequestMapping(value="/aluno/{codigoaluno}", method=RequestMethod.GET, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView getTabelaOcorrenciasPorAluno(@PathVariable("codigoaluno") Long codigo){
		System.out.println("chegou no metodo até que en fim....");
		System.out.println("aluno: "+codigo);
		ModelAndView mv = new ModelAndView("ocorrencia/TabelaOcorrenciasPorAluno");
		Aluno aluno = new Aluno();
		aluno.setCodigo(codigo);
		mv.addObject("ocorrencias", ocorrencias.findByAlunoOrderByDataRegistroDesc(aluno));
		return mv;
	}
}
