package br.edu.ifbaiano.csi.ngti.cae.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifbaiano.csi.ngti.cae.controller.page.PageWrapper;
import br.edu.ifbaiano.csi.ngti.cae.model.Alojamento;
import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.model.GrauParentesco;
import br.edu.ifbaiano.csi.ngti.cae.model.Ocorrencia;
import br.edu.ifbaiano.csi.ngti.cae.model.Regime;
import br.edu.ifbaiano.csi.ngti.cae.model.Responsavel;
import br.edu.ifbaiano.csi.ngti.cae.model.ResponsavelAluno;
import br.edu.ifbaiano.csi.ngti.cae.model.SerieTurma;
import br.edu.ifbaiano.csi.ngti.cae.model.Sexo;
import br.edu.ifbaiano.csi.ngti.cae.model.Status;
import br.edu.ifbaiano.csi.ngti.cae.model.TipoEncaminhamento;
import br.edu.ifbaiano.csi.ngti.cae.repository.Alunos;
import br.edu.ifbaiano.csi.ngti.cae.repository.Cursos;
import br.edu.ifbaiano.csi.ngti.cae.repository.Ocorrencias;
import br.edu.ifbaiano.csi.ngti.cae.repository.Responsaveis;
import br.edu.ifbaiano.csi.ngti.cae.repository.ResponsavelAlunos;
import br.edu.ifbaiano.csi.ngti.cae.repository.filter.AlunoFilter;
import br.edu.ifbaiano.csi.ngti.cae.security.UsuarioSistema;
import br.edu.ifbaiano.csi.ngti.cae.service.CadastroAlunoService;
import br.edu.ifbaiano.csi.ngti.cae.service.CadastroResponsavelAlunoService;
import br.edu.ifbaiano.csi.ngti.cae.service.exception.AlunoNumeroMatriculaJaCadastradoException;
import br.edu.ifbaiano.csi.ngti.cae.session.ListaAlunosSession;

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
	private ResponsavelAlunos responsaveisAluno;
	
	@Autowired
	private ListaAlunosSession listaAlunosSession;
	
	/**
	 * Adicionar alunos em uma lista na sessão do usuário logado 
	 * para que possa ser registrada uma mesma ocorrência a vários alunos de uma só vez
	 * 
	 * @param codigosAlunos Long[]
	 * @param uuid String
	 * @return List<Aluno>
	 */
	@PostMapping(value="/adicionar")
	public @ResponseBody ResponseEntity<?> adicionarAluno(@RequestParam("codigos[]") List<Long> codigos, @RequestParam("uuid") String uuid){
		listaAlunosSession.adicionarAluno(uuid, alunos.findByCodigoIn(codigos));
		return ResponseEntity.ok(listaAlunosSession.getAlunos(uuid));
	}
	
	/**
	 *	Recupera da sessao do usuario a lista de alunos selecionados para o registro da ocorrencia
	 *	@param uuid String
	 *	@return List<Aluno> alunos
	 */
	@GetMapping(value="/listar")
	public @ResponseBody ResponseEntity<?> listarAlunos(@RequestParam("uuid") String uuid){
		//TODO: logs
		System.out.println("------------> uuid: "+uuid);
		System.out.println("/listar -> quantidade de slunos: "+listaAlunosSession.totalAlunos(uuid));
		
		return ResponseEntity.ok(listaAlunosSession.getAlunos(uuid));
	}
	
	@DeleteMapping(value="/remover-todos/{uuid}")
	public @ResponseBody ResponseEntity<?> removerTodosAlunos(@PathVariable("uuid") String uuid){
		listaAlunosSession.excluirTodosOsAlunos(uuid);
		return ResponseEntity.ok(listaAlunosSession.getAlunos(uuid));
	} 
	
	
	
	@DeleteMapping(value="/remover/{uuid}/{codigo}")
	public @ResponseBody ResponseEntity<?> removerAluno(@PathVariable("uuid") String uuid, @PathVariable("codigo") Long codigo){
		Aluno aluno = new Aluno();
		aluno.setCodigo(codigo);
		listaAlunosSession.excluirAluno(uuid, aluno);
		return ResponseEntity.ok(listaAlunosSession.getAlunos(uuid));
	} 
	
	@GetMapping
	public ModelAndView pesquisar(AlunoFilter alunoFilter, @PageableDefault(size=10) Pageable pageable, HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView("aluno/PesquisaAlunos");
		mv.addObject("sexo", Sexo.values());
		mv.addObject("regimes", Regime.values());
		mv.addObject("series", SerieTurma.values());
		mv.addObject("cursos", cursos.findAll());
		mv.addObject("alojamentos", Alojamento.values());
		mv.addObject("statusList", Status.values());
		
		PageWrapper<Aluno> paginaWrapper = new PageWrapper<>(alunos.filtrar(alunoFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	@GetMapping(value="/filtrar")
	public @ResponseBody ResponseEntity<?> filtrar(@Valid AlunoFilter alunoFilter){
		return ResponseEntity.ok(alunos.filtroAdicionarNaOcorrencia(alunoFilter));
	}
	
	@RequestMapping(value="/por",consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Aluno> pesquisarAluno(@RequestParam("nomeOuMatricula") String nomeOuMatricula){
		return alunos.porNomeOuMatricula(nomeOuMatricula);
	}
	
	@RequestMapping(value="/por-matricula", consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> consultarDadosDoAluno(@RequestParam("matricula") String matricula){
		
		Optional<Aluno> alunoOptional = alunos.findByMatricula(matricula);
		if(!alunoOptional.isPresent()){
			return ResponseEntity.badRequest().body("Aluno não encontrado");
		}
		Aluno aluno = alunoOptional.get();
		aluno.setResponsaveisDoAluno(responsaveisAluno.findByAluno(aluno));
		
		return ResponseEntity.ok(aluno);
	}
	
	@GetMapping("/novo")
	public ModelAndView novo(Aluno aluno){
		ModelAndView mv  = new ModelAndView("aluno/CadastroAluno");
		mv.addObject("aluno", aluno);
		mv.addObject("sexo", Sexo.values());
		mv.addObject("regimes", Regime.values());
		mv.addObject("series", SerieTurma.values());
		mv.addObject("cursos", cursos.findAll());
		mv.addObject("parentescos", GrauParentesco.values());
		mv.addObject("alojamentos", Alojamento.values());
		mv.addObject("uuid", UUID.randomUUID().toString());
		
		return mv;
	}
	
	@PostMapping(value = { "/novo", "{\\d+}" })
	public ModelAndView salvar(@RequestParam("uuid") String uuid, @Valid Aluno aluno, BindingResult result, RedirectAttributes attributs){
		
		if(result.hasErrors()){
			 System.out.println("tem erros no formulário ------------->>>"); return novo(aluno);}
		
		//TODO: criar validação customizada para o cadastro do aluno
		/*if(tabelasResponsaveisSession.totalResponsaveis(uuid) == 0 && aluno.isNovo()){
			result.rejectValue("temResponsavel", "Adicione ao menos um responsavel", "Adicione ao menos um responsavel");
			System.out.println("Adicione ao menos um responsavel!");
			return novo(aluno);
		}*/
		
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
	public ModelAndView detalhesAluno(@RequestParam("matricula") String matricula, RedirectAttributes attributs){
		ModelAndView mv = new ModelAndView("aluno/DetalhesAluno");
		Optional<Aluno> alunoOptional = alunos.findByMatricula(matricula);
		if(alunoOptional.isPresent()){
			Aluno aluno = alunoOptional.get();
			Ocorrencia ocorrencia = new Ocorrencia();
			ocorrencia.setAluno(aluno);
			mv.addObject("aluno", aluno);
			mv.addObject("ocorrencias", ocorrencias.findByAlunoOrderByDataRegistroDesc(aluno));
			mv.addObject("ocorrencia", ocorrencia);
			mv.addObject("tipoEncaminhamento", TipoEncaminhamento.values());
			mv.addObject("status", Status.class);
		}else{
			attributs.addAttribute("warnning", "O numero da matricula nao foi encontrado");
		}
		
		return mv;
	}
	
	@Autowired
	private CadastroResponsavelAlunoService cadastroResponsavelAlunoService;
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Aluno aluno){
		aluno.setResponsaveisDoAluno(responsaveisAluno.findByAluno(aluno));
		
		/*ResponsavelAluno responsavelAluno = new ResponsavelAluno();
		Aluno alunoo = new Aluno();
		alunoo.setCodigo(531L);
		responsavelAluno.setAluno(alunoo);
		Responsavel responsavel = new Responsavel();
		responsavel.setCodigo(2L);
		responsavelAluno.setResponsavel(responsavel);
		responsavelAluno.setParentesco(GrauParentesco.PADASTRO);
		
		cadastroResponsavelAlunoService.salvar(responsavelAluno);*/
		
		return novo(aluno);
	}
	
	@GetMapping("/pesquisa")
	public ModelAndView pesquisaDadosAluno(){
		ModelAndView mv = new ModelAndView("aluno/ConsultarDadosAluno");
		return mv;
	}
}
