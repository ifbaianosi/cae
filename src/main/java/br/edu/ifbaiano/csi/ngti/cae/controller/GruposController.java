package br.edu.ifbaiano.csi.ngti.cae.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifbaiano.csi.ngti.cae.controller.page.PageWrapper;
import br.edu.ifbaiano.csi.ngti.cae.model.Grupo;
import br.edu.ifbaiano.csi.ngti.cae.repository.Grupos;
import br.edu.ifbaiano.csi.ngti.cae.repository.Permissoes;
import br.edu.ifbaiano.csi.ngti.cae.repository.filter.GrupoFilter;
import br.edu.ifbaiano.csi.ngti.cae.service.CadastroGrupoService;
import br.edu.ifbaiano.csi.ngti.cae.service.exception.GrupoJaCadastradoException;

@Controller
@RequestMapping("/perfis")
public class GruposController {
	
	
	@Autowired
	private Grupos grupos;
	
	@Autowired
	private Permissoes permissoes;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	@GetMapping
	public ModelAndView pesquisar(GrupoFilter grupoFilter, @PageableDefault(size=10) Pageable pageable, HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView("grupo/PesquisaGrupos");
		
		PageWrapper<Grupo> paginaWrapper = new PageWrapper<>(grupos.filtrar(grupoFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}

	@GetMapping(value="/novo")
	public ModelAndView novo(Grupo grupo){
		ModelAndView mv = new ModelAndView("grupo/CadastroGrupo");
		mv.addObject("grupo", grupo);
		mv.addObject("permissoes", permissoes.findAll());
		return mv;
	}
	

	@PostMapping(value={ "/novo", "{\\d+}" })
	public ModelAndView salvar(@Valid Grupo grupo, BindingResult result, RedirectAttributes attributs){
		
		if(result.hasErrors()){
			return novo(grupo);
		}
		
		try {
			System.out.println("=======> antes do salvar");
			cadastroGrupoService.salvar(grupo);
		} catch (GrupoJaCadastradoException e) {
			System.out.println("=======> grupo já cadastrado!!");
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(grupo);
		}
			
		attributs.addFlashAttribute("mensagemSucesso", "Grupo salvo com sucesso!");
		
		return new ModelAndView(grupo.isNovo() ? "redirect:/perfis/novo" : "redirect:/perfis/"+grupo.getCodigo());
	}

	 
	@GetMapping(value="/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long codigo){
		Grupo grupoOptional = grupos.porCodigoComPermissoes(codigo);
			/*System.out.println("===========> permissoes do grupo " + grupoOptional.getNome() + ": "+grupoOptional.getPermissoes().size());*/
			return novo(grupoOptional);
	}
}
