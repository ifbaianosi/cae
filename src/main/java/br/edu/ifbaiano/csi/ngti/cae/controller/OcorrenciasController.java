package br.edu.ifbaiano.csi.ngti.cae.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifbaiano.csi.ngti.cae.controller.page.PageWrapper;
import br.edu.ifbaiano.csi.ngti.cae.dto.OcorrenciaDTO;
import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.model.Ocorrencia;
import br.edu.ifbaiano.csi.ngti.cae.repository.Alunos;
import br.edu.ifbaiano.csi.ngti.cae.repository.Ocorrencias;
import br.edu.ifbaiano.csi.ngti.cae.repository.filter.OcorrenciaFilter;
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
	
	@GetMapping
	public ModelAndView pesquisar(OcorrenciaFilter ocorrenciaFilter, @PageableDefault(size=10) Pageable pageable, HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView("ocorrencia/PesquisaOcorrencias");
		
		PageWrapper<Ocorrencia> paginaWrapper = new PageWrapper<>(ocorrencias.filtrar(ocorrenciaFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
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
	public ResponseEntity<?> salvarViaAjax(@Valid Ocorrencia ocorrencia, BindingResult result){
		if(result.hasErrors()){
			return ResponseEntity.badRequest().build();
		}
		
		cadastroOcorrenciaService.salvar(ocorrencia);
		
		return ResponseEntity.ok().body(HttpStatus.CREATED);
	}
	
	/*@PostMapping(value="/salvar")
	public ModelAndView salvarViaAjax(@Valid Ocorrencia ocorrencia, BindingResult result, RedirectAttributes attributs){
		ModelAndView mv = new ModelAndView("ocorrencia/Formulario");
		if(result.hasErrors()){
			mv.addObject("ocorrencia", ocorrencia);
			return mv;
		}
		
		cadastroOcorrenciaService.salvar(ocorrencia);
		
		return formulario(ocorrencia.getAluno().getCodigo());
	}*/
	
	@PostMapping(value={ "/nova", "{\\d+}" })
	public ModelAndView salvar(@Valid Ocorrencia ocorrencia, BindingResult result, RedirectAttributes attributs){
		
		if(result.hasErrors())
			return nova(ocorrencia);
		
		cadastroOcorrenciaService.salvar(ocorrencia);
			
		attributs.addFlashAttribute("mensagemSucesso", "Ocorrencia salva com sucesso");
		
		return new ModelAndView("redirect:/ocorrencias/nova");
	}
	
	@RequestMapping(value="/aluno/{codigoaluno}", method=RequestMethod.GET, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView getTabelaOcorrenciasPorAluno(@PathVariable("codigoaluno") Aluno aluno){
		ModelAndView mv = new ModelAndView("ocorrencia/TabelaOcorrenciasPorAluno");
		mv.addObject("ocorrencias", ocorrencias.findByAlunoOrderByDataRegistroDesc(aluno));
		return mv;
	}
	
	@GetMapping(value="/{codigoaluno}")
	public @ResponseBody List<OcorrenciaDTO> getOcorencias(@PathVariable("codigoaluno") Aluno aluno){
		return ocorrencias.porAluno(aluno);
	}
	
	@GetMapping(value="/quantidade/{codigoaluno}")
	public @ResponseBody Long getQuantidadeOcorenciasPorAluno(@PathVariable("codigoaluno") Aluno aluno){
		return ocorrencias.countByAluno(aluno);
	}
	
	@GetMapping(value="/ver/{codigoocorrencia}")
	public @ResponseBody OcorrenciaDTO getOcorrencia(@PathVariable("codigoocorrencia") Long codigo){
		return ocorrencias.porCodigo(codigo);
	}
	
	@DeleteMapping(value="/{codigos}")
	public ResponseEntity<?> excluir(@PathVariable Long[] codigos){
		
		try {
			cadastroOcorrenciaService.excluir(codigos);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Não foi possível escluir! "+e.getMessage());
		}
		
		return ResponseEntity.ok().build();
	}
}
