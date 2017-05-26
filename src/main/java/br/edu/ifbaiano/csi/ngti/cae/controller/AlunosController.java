package br.edu.ifbaiano.csi.ngti.cae.controller;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.model.GrauParentesco;
import br.edu.ifbaiano.csi.ngti.cae.model.Identificacao;
import br.edu.ifbaiano.csi.ngti.cae.model.Ocorrencia;
import br.edu.ifbaiano.csi.ngti.cae.model.SerieTurma;
import br.edu.ifbaiano.csi.ngti.cae.model.Sexo;
import br.edu.ifbaiano.csi.ngti.cae.repository.Alunos;
import br.edu.ifbaiano.csi.ngti.cae.repository.Cursos;
import br.edu.ifbaiano.csi.ngti.cae.repository.Ocorrencias;
import br.edu.ifbaiano.csi.ngti.cae.service.CadastroAlunoService;
import br.edu.ifbaiano.csi.ngti.cae.service.exception.AlunoNumeroMatriculaJaCadastradoException;
import br.edu.ifbaiano.csi.ngti.cae.session.TabelasResponsaveisSession;

@Controller
@RequestMapping("/alunos")
public class AlunosController {
	
	@Autowired
	private Alunos alunos;
	
	@Autowired
	private Ocorrencias ocorrencias;
	
	@Autowired
	private Cursos cursos;
	
	@Autowired
	private CadastroAlunoService cadastroAlunoService;
	
	@Autowired
	private TabelasResponsaveisSession tabelasResponsaveisSession;
	
	@GetMapping
	public String pesquisar(){
		return "aluno/PesquisaAlunos";
	}
	
	@GetMapping("/novo")
	public ModelAndView novo(Aluno aluno){
		ModelAndView mv  = new ModelAndView("aluno/CadastroAluno");
		mv.addObject("sexo", Sexo.values());
		mv.addObject("identificacoes", Identificacao.values());
		mv.addObject("series", SerieTurma.values());
		mv.addObject("cursos", cursos.findAll());
		mv.addObject("parentescos", GrauParentesco.values());
		mv.addObject("uuid", UUID.randomUUID().toString());
		
		return mv;
	}
	
	@PostMapping(value = { "/novo", "{\\d+}" })
	public ModelAndView salvar(@RequestParam("uuid") String uuid, @Valid Aluno aluno, BindingResult result, RedirectAttributes attributs){
		
		if(result.hasErrors()){
			 System.out.println("tem erros no formulário ------------->>>"); return novo(aluno);}
		
		//TODO: criar validação customizada para o cadastro do aluno
		if(tabelasResponsaveisSession.totalResponsaveis(uuid) == 0){
			result.rejectValue("temResponsavel", "Adicione ao menos um responsavel", "Adicione ao menos um responsavel");
			System.out.println("Adicione ao menos um responsavel!");
			return novo(aluno);
		}
		
		try {
			cadastroAlunoService.salvar(aluno, uuid);
		} catch (AlunoNumeroMatriculaJaCadastradoException e) {
			result.rejectValue("matricula", e.getMessage(), e.getMessage());
			return novo(aluno);
		}

		attributs.addFlashAttribute("mensagemSucesso", "Aluno salvo com sucesso!");
		
		return new ModelAndView("redirect:/alunos/novo");
	}
	
	@GetMapping("/detalhe")
	public ModelAndView detalhesAluno(@RequestParam("matricula") String matricula){
		ModelAndView mv = new ModelAndView("aluno/DetalhesAluno");
		Optional<Aluno> alunoOptional = alunos.findByMatricula(matricula);
		if(alunoOptional.isPresent()){
			Aluno aluno = alunoOptional.get();
			Ocorrencia ocorrencia = new Ocorrencia();
			ocorrencia.setAluno(aluno);
			mv.addObject("aluno", aluno);
			mv.addObject("ocorrencias", ocorrencias.findByAlunoOrderByDataRegistroDesc(aluno));
			mv.addObject("ocorrencia", ocorrencia);
		}
		
		return mv;
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
