package br.edu.ifbaiano.csi.ngti.cae.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;

@Controller
@RequestMapping("/alunos")
public class AlunosController {

	@GetMapping
	public String pesquisar(){
		return "aluno/PesquisaAlunos";
	}
	
	@GetMapping("/novo")
	public String novo(){
		return "aluno/CadastroAluno";
	}
	
	@PostMapping(value = { "/novo", "{\\d+}" })
	public String salvar(){
		return "aluno/CadastroAluno";
	}
	
	@GetMapping("/{codigo}")
	public String editar(@PathVariable("codigo") Aluno aluno){
		return "aluno/CadastroAluno";
	}
}
