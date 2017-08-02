package br.edu.ifbaiano.csi.ngti.cae.controller;



import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class SegurancaController {
	
	@GetMapping("/login")
	public String paginaLogin(@AuthenticationPrincipal User user){
		if(user != null)
			return "redirect:/";
		
		return "Login";
	}

	@GetMapping("/403")
	public String acessoNegado() {
		return "403";
	}
	
	/*@GetMapping("/404")
	public String paginaNaoEncontrada() {
		return "404";
	}*/
	
	@GetMapping("/500")
	public String erroServidor() {
		return "500";
	}
}
