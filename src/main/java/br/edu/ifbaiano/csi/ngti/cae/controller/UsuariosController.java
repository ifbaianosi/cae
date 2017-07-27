package br.edu.ifbaiano.csi.ngti.cae.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifbaiano.csi.ngti.cae.controller.page.PageWrapper;
import br.edu.ifbaiano.csi.ngti.cae.model.Encaminhamento;
import br.edu.ifbaiano.csi.ngti.cae.model.SenhaUsuario;
import br.edu.ifbaiano.csi.ngti.cae.model.Usuario;
import br.edu.ifbaiano.csi.ngti.cae.repository.Grupos;
import br.edu.ifbaiano.csi.ngti.cae.repository.Usuarios;
import br.edu.ifbaiano.csi.ngti.cae.repository.filter.UsuarioFilter;
import br.edu.ifbaiano.csi.ngti.cae.security.UsuarioSistema;
import br.edu.ifbaiano.csi.ngti.cae.service.CadastroUsuarioService;
import br.edu.ifbaiano.csi.ngti.cae.service.StatusUsuario;
import br.edu.ifbaiano.csi.ngti.cae.service.exception.ImpossivelExcluirEntidadeException;
import br.edu.ifbaiano.csi.ngti.cae.service.exception.SenhaObrigatoriaUsuarioException;
import br.edu.ifbaiano.csi.ngti.cae.service.exception.UsuarioEmailJaCadastradoException;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private Grupos grupos;
	
	@Autowired private Usuarios usuarios;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter usuarioFilter, @PageableDefault(size=10) Pageable pageable, HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView("usuario/PesquisaUsuarios");
		
		PageWrapper<Usuario> paginaWrapper = new PageWrapper<>(usuarios.filtrar(usuarioFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
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
	

	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestParam("codigos[]") Long[] codigos, @RequestParam("status") StatusUsuario statusUsuario) {
		cadastroUsuarioService.alterarStatus(codigos, statusUsuario);
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Usuario usuario = usuarios.buscarComGrupos(codigo);
		ModelAndView mv = novo(usuario);
		mv.addObject(usuario);
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Usuario usuario) {
		try {
			cadastroUsuarioService.excluir(usuario);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/trocarSenha")
	public ResponseEntity<?> alterarSenha(@Valid SenhaUsuario senhaUsuario, BindingResult result, @AuthenticationPrincipal UsuarioSistema usuarioSistema){
		List<String> errosStr = new ArrayList<>();
		
		if(result.hasErrors()){
			result.getFieldErrors().stream().forEach(error -> errosStr.add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(errosStr);
		}
		
		if(!senhaUsuario.isValid(senhaUsuario.getSenha(), senhaUsuario.getConfirmacaoSenha())){
			errosStr.add("A nova senha e a confirmação de senha não conferem");
			return ResponseEntity.badRequest().body(errosStr);
		}
		
		String novaSenhaInformada = this.passwordEncoder.encode(senhaUsuario.getSenha());
		
		/*System.out.println("novaSenhaInformada: "+novaSenhaInformada);
		System.out.println("usuarioSistema.getUsuario().getSenha(): "+usuarioSistema.getUsuario().getSenha());
				
		if(!usuarioSistema.getUsuario().getSenha().equals(novaSenhaInformada)){
			errosStr.add("Senha atual não está correta");
			return ResponseEntity.badRequest().body(errosStr);
		}*/
		
		Usuario usuario = usuarioSistema.getUsuario();
		usuario.setSenha(novaSenhaInformada);
		cadastroUsuarioService.trocarSenha(usuario);
		
		return ResponseEntity.ok().body(HttpStatus.OK);
	}
}
