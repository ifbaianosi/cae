package br.edu.ifbaiano.csi.ngti.cae.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifbaiano.csi.ngti.cae.repository.Alunos;
import br.edu.ifbaiano.csi.ngti.cae.repository.Ocorrencias;


@Controller
@RequestMapping("/")
public class InicioController {
	
	@Autowired
	private Ocorrencias ocorrencias;
	
	@Autowired
	private Alunos alunos;
	
	@GetMapping
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView("Index");
		mv.addObject("quantidadeOcorrencias", ocorrencias.count());
		mv.addObject("quantidadeAlunos", alunos.count());
		
		return mv;
	}


}
