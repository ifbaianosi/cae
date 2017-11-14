package br.edu.ifbaiano.csi.ngti.cae.controller;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifbaiano.csi.ngti.cae.repository.Alunos;
import br.edu.ifbaiano.csi.ngti.cae.repository.Encaminhamentos;
import br.edu.ifbaiano.csi.ngti.cae.repository.Ocorrencias;
import br.edu.ifbaiano.csi.ngti.cae.repository.Responsaveis;
import br.edu.ifbaiano.csi.ngti.cae.security.UsuarioSistema;


@Controller
@RequestMapping("/")
public class InicioController {
	
	@Autowired
	private Ocorrencias ocorrencias;
	
	@Autowired
	private Encaminhamentos encaminhamentos;
	
	@Autowired
	private Alunos alunos;
	
	@Autowired
	private Responsaveis responsaveis;
	
	@GetMapping
	public ModelAndView index(@AuthenticationPrincipal UsuarioSistema usuarioSistema, HttpServletRequest request){
		ModelAndView mv = new ModelAndView("Index");
		
		//Obter a quantidade de ocorrencias de acordo com as permissões do usuario logado
		Long quantidadeOcorrencias = 0L;
		if(request.isUserInRole("PESQUISAR_TODAS_OCORRENCIAS"))
			quantidadeOcorrencias = ocorrencias.count();
		else quantidadeOcorrencias = ocorrencias.countByUsuario(usuarioSistema.getUsuario());
			
		mv.addObject("quantidadeOcorrencias", quantidadeOcorrencias);
		mv.addObject("quantidadeAlunos", alunos.count());
		mv.addObject("quantidadeEncaminhamentos", encaminhamentos.count());
		mv.addObject("ocorrenciasPorLocal", ocorrencias.totalOcorrenciasPorLocal());
		mv.addObject("ocorrenciasPorUsuario", ocorrencias.totalOcorrenciasPorUsuario());
		mv.addObject("ocorrenciasPorAluno", ocorrencias.totalOcorrenciasPorAluno());
		mv.addObject("quantidadeResponsaveis", responsaveis.count());
		
		return mv;
	}


}
