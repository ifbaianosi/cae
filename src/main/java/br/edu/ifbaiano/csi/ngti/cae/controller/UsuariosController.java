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

import br.edu.ifbaiano.csi.ngti.cae.model.Usuario;
import br.edu.ifbaiano.csi.ngti.cae.repository.Grupos;
import br.edu.ifbaiano.csi.ngti.cae.service.CadastroUsuarioService;
import br.edu.ifbaiano.csi.ngti.cae.service.exception.SenhaObrigatoriaUsuarioException;
import br.edu.ifbaiano.csi.ngti.cae.service.exception.UsuarioEmailJaCadastradoException;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private Grupos grupos;
	
	@GetMapping("/novo")
	public ModelAndView novo(Usuario usuario){
		ModelAndView mv  = new ModelAndView("usuario/CadastroUsuario");
		mv.addObject("grupos", grupos.findAll());
		
		return mv;
	}
	
	@PostMapping(value = { "/novo", "{\\d+}" })
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributs){
		
		if(result.hasErrors()){
			 System.out.println("tem erros no formulário ------------->>>"); return novo(usuario);}
		
		
		try {
			cadastroUsuarioService.salvar(usuario);
		} catch (UsuarioEmailJaCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return novo(usuario);
		} catch (SenhaObrigatoriaUsuarioException e) {
			result.rejectValue("senha", e.getMessage(), e.getMessage());
			return novo(usuario);
		}

		attributs.addFlashAttribute("mensagemSucesso", "Usuario salvo com sucesso!");
		
		return new ModelAndView("redirect:/usuarios/novo");
	}
}
