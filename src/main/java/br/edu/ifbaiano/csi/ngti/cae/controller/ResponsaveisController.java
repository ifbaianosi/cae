package br.edu.ifbaiano.csi.ngti.cae.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifbaiano.csi.ngti.cae.controller.page.PageWrapper;
import br.edu.ifbaiano.csi.ngti.cae.model.Alojamento;
import br.edu.ifbaiano.csi.ngti.cae.model.Ocorrencia;
import br.edu.ifbaiano.csi.ngti.cae.model.Responsavel;
import br.edu.ifbaiano.csi.ngti.cae.model.ResponsavelAluno;
import br.edu.ifbaiano.csi.ngti.cae.model.SerieTurma;
import br.edu.ifbaiano.csi.ngti.cae.repository.Responsaveis;
import br.edu.ifbaiano.csi.ngti.cae.repository.filter.ResponsavelFilter;
import br.edu.ifbaiano.csi.ngti.cae.security.UsuarioSistema;
import br.edu.ifbaiano.csi.ngti.cae.service.CadastroResponsavelAlunoService;
import br.edu.ifbaiano.csi.ngti.cae.service.CadastroResponsavelService;
import br.edu.ifbaiano.csi.ngti.cae.service.exception.ResponsavelJaCadastradoException;
import br.edu.ifbaiano.csi.ngti.cae.session.TabelasResponsaveisSession;

@Controller
@RequestMapping("/responsaveis")
public class ResponsaveisController {
			
	@Autowired
	private TabelasResponsaveisSession tabelasResponsaveisSession;
	
	@Autowired
	private CadastroResponsavelService cadastroResponsavelService;
	
	@Autowired
	private Responsaveis responsaveis;
	
	@Autowired
	private CadastroResponsavelAlunoService cadastroResponsavelAlunoService;
	
	@GetMapping
	public ModelAndView pesquisar(ResponsavelFilter responsavelFilter, @PageableDefault(size=10) Pageable pageable, HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView("responsavel/PesquisaResponsaveis");
		PageWrapper<Responsavel> paginaWrapper = new PageWrapper<>(responsaveis.filtrar(responsavelFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	@GetMapping(value="/novo")
	public ModelAndView novo(Responsavel responsavel){
		ModelAndView mv = new ModelAndView("responsavel/CadastroResponsavel");
		mv.addObject("responsavel", responsavel);
		return mv;
	}
	
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> salvarResponsavel(@RequestBody @Valid Responsavel responsavel, BindingResult result){
		
		if(result.hasErrors()){
			StringBuilder sb = formatarErros(result);
			return ResponseEntity.badRequest().body(sb.toString());
		}
		
		Responsavel responsavelGravado;
		try {
			responsavelGravado = cadastroResponsavelService.salvar(responsavel);
		} catch (ResponsavelJaCadastradoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		
		return ResponseEntity.ok(responsavelGravado);
	}
	
	@PostMapping(value={ "/novo", "{\\d+}" })
	public ModelAndView salvar(@Valid Responsavel responsavel, BindingResult result, RedirectAttributes attributs){
		
		if(result.hasErrors()){
			return novo(responsavel);
		}
		
		try {
			cadastroResponsavelService.salvar(responsavel);
		} catch (ResponsavelJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(responsavel);
		}
			
		attributs.addFlashAttribute("mensagemSucesso", "Responsável salvo com sucesso");
		
		return new ModelAndView("redirect:/responsaveis/novo");
	}
	
	@GetMapping(value="/por-nome/{nome}")//verificar a pesquisa que esta quebrada pois alterei a url de acesso ao metodo
	public @ResponseBody ResponseEntity<?> responsaveisPorNome(@PathVariable("nome") String nome){
		List<Responsavel> responsaveisList = null;
		responsaveisList = responsaveis.findByNomeStartsWithIgnoreCase(nome);
		return ResponseEntity.ok(responsaveisList);
	}
	

	private StringBuilder formatarErros(BindingResult result) {
		StringBuilder sb = new StringBuilder();
		for (ObjectError error : result.getAllErrors()) {
			sb.append(error.getDefaultMessage());
			sb.append("</br>");
		}
		return sb;
	}
	
	@PostMapping(value="/adicionar", consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> adicionarResponsavelAoAluno(@RequestBody @Valid ResponsavelAluno responsavelAluno, BindingResult result){
		System.out.println("ENTROU NO METODO =============>");
		if(result.hasErrors()){
			return ResponseEntity.badRequest().body(formatarErros(result).toString());
		}
		
		cadastroResponsavelAlunoService.salvar(responsavelAluno);
		
		return ResponseEntity.ok().build();
	}
	
	/*@PostMapping(value="/adicionar", consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> adicionarResponsavel(@RequestBody @Valid Responsavel responsavel, BindingResult result){
		
		if(result.hasErrors())
			return ResponseEntity.badRequest().build();
		
		List<Responsavel> responsaveis = new ArrayList<>();
		responsaveis.add(responsavel);
		
		tabelasResponsaveisSession.adicionarResponsavel(responsavel.getUuid(), responsaveis);
		
		//TODO: logs
		System.out.println("------------> uuid: "+responsavel.getUuid());
		System.out.println("/adicionar -> quantidade de responsaveis: "+tabelasResponsaveisSession.totalResponsaveis(responsavel.getUuid()));
		
		return ResponseEntity.ok(tabelasResponsaveisSession.getResponsaveis(responsavel.getUuid()));
	}*/
	
	@DeleteMapping(value="/remover-todos/{uuid}")
	public @ResponseBody ResponseEntity<?> removerTodosResponsaveis(@PathVariable("uuid") String uuid){
		
		//TODO: logs
		System.out.println("------------> uuid: "+uuid);
		System.out.println("/remover-todos -> quantidade de responsaveis: "+tabelasResponsaveisSession.totalResponsaveis(uuid));
		
		tabelasResponsaveisSession.excluirTodosOsResponsaveis(uuid);
		
		//TODO: logs
		System.out.println("/remover-todos -> quantidade de responsaveis: "+tabelasResponsaveisSession.totalResponsaveis(uuid));
		
		return ResponseEntity.ok(tabelasResponsaveisSession.getResponsaveis(uuid));
	} 
	
	@DeleteMapping(value="/remover/{uuid}/{contato}")
	public @ResponseBody ResponseEntity<?> removerResponsavel(@PathVariable("uuid") String uuid, @PathVariable("contato") String contato){
		Responsavel responsavel = new Responsavel();
		responsavel.setContato(contato);
		
		//TODO: logs
		System.out.println("/remover antes-> quantidade de responsaveis: "+tabelasResponsaveisSession.totalResponsaveis(uuid));
		
		tabelasResponsaveisSession.excluirResponsavel(uuid, responsavel);
		
		//TODO: logs
		System.out.println("/remover depois de excluir-> quantidade de responsaveis: "+tabelasResponsaveisSession.totalResponsaveis(uuid));
		
		return ResponseEntity.ok(tabelasResponsaveisSession.getResponsaveis(uuid));
	} 
	
	 
	@GetMapping(value="/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long codigo){
		Optional<Responsavel> responsavelOpt = responsaveis.porCodigo(codigo);
		if(responsavelOpt.isPresent())
			return novo(responsavelOpt.get());
		
		return novo(new Responsavel());
	}
	
	@DeleteMapping(value="/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Long codigo){
		
		try {
			cadastroResponsavelService.excluir(codigo);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Não foi possível escluir! "+e.getMessage());
		}
		
		return ResponseEntity.ok().build();
	}
}
