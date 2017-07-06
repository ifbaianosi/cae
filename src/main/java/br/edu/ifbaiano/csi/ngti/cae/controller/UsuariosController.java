package br.edu.ifbaiano.csi.ngti.cae.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.model.Usuario;
import br.edu.ifbaiano.csi.ngti.cae.service.exception.AlunoNumeroMatriculaJaCadastradoException;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {
	
	@GetMapping("/novo")
	public ModelAndView novo(Usuario usuario){
		ModelAndView mv  = new ModelAndView("usuario/CadastroUsuario");
		mv.addObject("usuario", usuario);
		
		return mv;
	}
	
	@PostMapping(value = { "/novo", "{\\d+}" })
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributs){
		
		if(result.hasErrors()){
			 System.out.println("tem erros no formulário ------------->>>"); return novo(usuario);}
		
		
		/*try {
			cadastroUsuarioService.salvar(usuario, uuid);
		} catch (UsuarioNumeroMatriculaJaCadastradoException e) {
			result.rejectValue("matricula", e.getMessage(), e.getMessage());
			return novo(usuario);
		}*/

		attributs.addFlashAttribute("mensagemSucesso", "Usuario salvo com sucesso!");
		
		return new ModelAndView("redirect:/usuarios/novo");
	}
}
