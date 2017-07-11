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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import br.edu.ifbaiano.csi.ngti.cae.model.TipoEncaminhamento;
import br.edu.ifbaiano.csi.ngti.cae.repository.Alunos;
import br.edu.ifbaiano.csi.ngti.cae.repository.Ocorrencias;
import br.edu.ifbaiano.csi.ngti.cae.repository.Usuarios;
import br.edu.ifbaiano.csi.ngti.cae.repository.filter.OcorrenciaFilter;
import br.edu.ifbaiano.csi.ngti.cae.repository.helper.ocorrencia.OcorrenciasQueries;
import br.edu.ifbaiano.csi.ngti.cae.security.UsuarioSistema;
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
	
	@Autowired
	private Usuarios usuarios;
	
	@GetMapping
	public ModelAndView pesquisar(OcorrenciaFilter ocorrenciaFilter, @PageableDefault(size=10) Pageable pageable, HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView("ocorrencia/PesquisaOcorrencias");
		
		PageWrapper<Ocorrencia> paginaWrapper = new PageWrapper<>(ocorrencias.filtrar(ocorrenciaFilter, pageable), httpServletRequest);
		
		/*List<Ocorrencia> ocorrenciasList = paginaWrapper.getConteudo();
		System.err.println("encaminhamentos da ocorrencia 0: "+ocorrenciasList.get(0).getEncaminhamentos().get(0).getDescricao());
		
		System.out.println("buscarComEncaminhamentos: "+ocorrencias.buscarComEncaminhamentos(new Aluno()).get(0).getEncaminhamentos().get(0).getDescricao());*/
		
		mv.addObject("pagina", paginaWrapper);
		mv.addObject("usuarios", usuarios.findByAtivoTrue());
		mv.addObject("tipoEncaminhamento", TipoEncaminhamento.values());
		
		return mv;
	}
	
	@GetMapping(value="/nova")
	public ModelAndView nova(Ocorrencia ocorrencia){
		ModelAndView mv = new ModelAndView("ocorrencia/CadastroOcorrencia"); 
		mv.addObject("ocorrencia", ocorrencia);
		
		return mv;
	}
	
	@GetMapping(value="/formulario/{codigoaluno}")
	public ModelAndView formulario(@PathVariable("codigoaluno") Long codigoaluno){
		ModelAndView mv = new ModelAndView("ocorrencia/Formulario");
		
		Ocorrencia ocorrencia = new Ocorrencia();
		ocorrencia.setAluno(alunos.findOne(codigoaluno));
		mv.addObject("ocorrencia", ocorrencia);
		
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
	
	@PostMapping(value={ "/nova", "{\\d+}" })
	public ModelAndView salvar(@Valid Ocorrencia ocorrencia, BindingResult result, @AuthenticationPrincipal UsuarioSistema usuarioSistema, RedirectAttributes attributs){
		
		ocorrencia.setUsuario(usuarioSistema.getUsuario());
		
		if(result.hasErrors()){
			System.out.println("erro no bean ocorrencia!!!");return nova(ocorrencia);}
		
		cadastroOcorrenciaService.salvar(ocorrencia);
			
		attributs.addFlashAttribute("mensagemSucesso", "Ocorrencia salva com sucesso");
		
		return new ModelAndView("redirect:/ocorrencias/nova");
	}
	
	@GetMapping(value="/aluno/{codigoaluno}")
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

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Ocorrencia ocorrencia){
		System.out.println("codigo da ocorrencia: "+ ocorrencia.getCodigo());
		return nova(ocorrencia);
	}
}
