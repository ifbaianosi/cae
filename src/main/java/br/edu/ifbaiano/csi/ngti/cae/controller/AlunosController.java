package br.edu.ifbaiano.csi.ngti.cae.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.service.CadastroAlunoService;

@Controller
@RequestMapping("/alunos")
public class AlunosController {
	
	@Autowired
	private CadastroAlunoService cadastroAlunoService;

	@GetMapping
	public String pesquisar(){
		return "aluno/PesquisaAlunos";
	}
	
	@GetMapping("/novo")
	public ModelAndView novo(Aluno aluno){
		ModelAndView mv  = new ModelAndView("aluno/CadastroAluno");
		mv.addObject("aluno", aluno);
		
		return mv;
	}
	
	@PostMapping(value = { "/novo", "{\\d+}" })
	public ModelAndView salvar(@Valid Aluno aluno, BindingResult result, RedirectAttributes attributs){
		
		if(result.hasErrors())
			return novo(aluno);
		
		cadastroAlunoService.salvar(aluno);

		attributs.addFlashAttribute("mensagemSucesso", "Aluno salvo com sucesso!");
		
		return new ModelAndView("redirect:/alunos/novo");
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Aluno aluno){
		/*Aluno aluno = new Aluno();
		aluno.setCodigo(codigo);
		aluno.setMatricula("123456");
		aluno.setNome("nome do aluno");
		
		System.out.println("chamou o metodo editar!");
		System.out.println("codigo do aluno: "+ aluno.getCodigo());*/
		return novo(aluno);
	}
}
