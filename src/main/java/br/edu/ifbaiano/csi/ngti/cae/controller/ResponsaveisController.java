package br.edu.ifbaiano.csi.ngti.cae.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.xml.ws.http.HTTPBinding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.edu.ifbaiano.csi.ngti.cae.model.Responsavel;
import br.edu.ifbaiano.csi.ngti.cae.model.ResponsavelAluno;
import br.edu.ifbaiano.csi.ngti.cae.repository.Responsaveis;
import br.edu.ifbaiano.csi.ngti.cae.repository.ResponsavelAlunos;
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
	public @ResponseBody ResponseEntity<?> responsaveisPorNome(@RequestParam("nome") String nome){
		return ResponseEntity.ok(responsaveis.findByNomeStartsWithIgnoreCase(nome));
	}
	
	@PostMapping(value="/novo", consumes=MediaType.APPLICATION_JSON_VALUE)
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
