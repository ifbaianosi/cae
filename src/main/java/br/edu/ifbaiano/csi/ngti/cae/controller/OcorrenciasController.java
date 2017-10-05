package br.edu.ifbaiano.csi.ngti.cae.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import br.edu.ifbaiano.csi.ngti.cae.dto.GraficoOcorrenciasPorMes;
import br.edu.ifbaiano.csi.ngti.cae.dto.OcorrenciaDTO;
import br.edu.ifbaiano.csi.ngti.cae.model.Alojamento;
import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.model.Notificacao;
import br.edu.ifbaiano.csi.ngti.cae.model.Ocorrencia;
import br.edu.ifbaiano.csi.ngti.cae.model.SerieTurma;
import br.edu.ifbaiano.csi.ngti.cae.model.TipoEncaminhamento;
import br.edu.ifbaiano.csi.ngti.cae.model.TipoNotificacao;
import br.edu.ifbaiano.csi.ngti.cae.model.UsuarioNotificacao;
import br.edu.ifbaiano.csi.ngti.cae.repository.Alunos;
import br.edu.ifbaiano.csi.ngti.cae.repository.Cursos;
import br.edu.ifbaiano.csi.ngti.cae.repository.Ocorrencias;
import br.edu.ifbaiano.csi.ngti.cae.repository.Usuarios;
import br.edu.ifbaiano.csi.ngti.cae.repository.filter.OcorrenciaFilter;
import br.edu.ifbaiano.csi.ngti.cae.security.UsuarioSistema;
import br.edu.ifbaiano.csi.ngti.cae.service.CadastroNotificacaoService;
import br.edu.ifbaiano.csi.ngti.cae.service.CadastroOcorrenciaService;
import br.edu.ifbaiano.csi.ngti.cae.service.CadastroUsuarioNotificacaoService;

@Controller
@RequestMapping("/ocorrencias")
public class OcorrenciasController {
	
	@Autowired
	private CadastroOcorrenciaService cadastroOcorrenciaService;
	
	@Autowired
	private Alunos alunos;
	
	@Autowired
	private Ocorrencias ocorrencias;
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private Cursos cursos;
	
	@GetMapping
	public ModelAndView pesquisar(OcorrenciaFilter ocorrenciaFilter, @PageableDefault(size=10) Pageable pageable, HttpServletRequest httpServletRequest, @AuthenticationPrincipal UsuarioSistema usuarioSistema){
		ModelAndView mv = new ModelAndView("ocorrencia/PesquisaOcorrencias");
		
		PageWrapper<Ocorrencia> paginaWrapper = new PageWrapper<>(ocorrencias.filtrar(ocorrenciaFilter, pageable), httpServletRequest);
		
		
		mv.addObject("pagina", paginaWrapper);
		mv.addObject("usuarios", usuarios.findByAtivoTrue());
		mv.addObject("tipoEncaminhamento", TipoEncaminhamento.values());
		mv.addObject("cursos", cursos.findAll());
		mv.addObject("series", SerieTurma.values());
		mv.addObject("alojamentos", Alojamento.values());
		
		return mv;
	}
	
	@GetMapping(value="/autoria")
	public ModelAndView pesquisarOcorrenciasPorUsuario(OcorrenciaFilter ocorrenciaFilter, @PageableDefault(size=10) Pageable pageable, HttpServletRequest httpServletRequest, @AuthenticationPrincipal UsuarioSistema usuarioSistema){
		ModelAndView mv = new ModelAndView("ocorrencia/PesquisaOcorrencias");
		
		ocorrenciaFilter.setUsuario(usuarioSistema.getUsuario());
		PageWrapper<Ocorrencia> paginaWrapper = new PageWrapper<>(ocorrencias.filtrar(ocorrenciaFilter, pageable), httpServletRequest);
		
		mv.addObject("pagina", paginaWrapper);
		Long [] codigos = {usuarioSistema.getUsuario().getCodigo()};
		mv.addObject("usuarios", usuarios.findByCodigoIn(codigos));
		mv.addObject("tipoEncaminhamento", TipoEncaminhamento.values());
		mv.addObject("cursos", cursos.findAll());
		mv.addObject("series", SerieTurma.values());
		mv.addObject("alojamentos", Alojamento.values());
		
		return mv;
	}
	
	@GetMapping(value="/nova")
	public ModelAndView nova(Ocorrencia ocorrencia){
		ModelAndView mv = new ModelAndView("ocorrencia/CadastroOcorrencia");
		
		if (ocorrencia.getUuid() == null){
			ocorrencia.setUuid(UUID.randomUUID().toString());
		}
		
		mv.addObject("ocorrencia", ocorrencia);
		mv.addObject("cursos", cursos.findAll());
		mv.addObject("alojamentos", Alojamento.values());
		mv.addObject("series", SerieTurma.values());
		
		return mv;
	}
	
	@GetMapping(value="/formulario/{codigoaluno}")
	public ModelAndView formulario(@PathVariable("codigoaluno") Long codigoaluno){
		ModelAndView mv = new ModelAndView("ocorrencia/Formulario");
		
		Ocorrencia ocorrencia = new Ocorrencia();
		ocorrencia.setAluno(alunos.findOne(codigoaluno));
		mv.addObject("ocorrencia", ocorrencia);
		
		return mv; 
	}
	
	@PostMapping(value="/salvar")
	public ResponseEntity<?> salvarViaAjax(@Valid Ocorrencia ocorrencia, BindingResult result, @AuthenticationPrincipal UsuarioSistema usuarioSistema){
		
		ocorrencia.setUsuario(usuarioSistema.getUsuario());
		
		if(result.hasErrors()){
			return ResponseEntity.badRequest().build();
		}
		cadastroOcorrenciaService.salvarAjax(ocorrencia);
		
		return ResponseEntity.ok().body(HttpStatus.CREATED);
	}
	
	@PostMapping(value={ "/nova", "{\\d+}" })
	public ModelAndView salvar(@Valid Ocorrencia ocorrencia, BindingResult result, @AuthenticationPrincipal UsuarioSistema usuarioSistema, RedirectAttributes attributs){
		
		if(ocorrencia.isNovo()){
			ocorrencia.setUsuario(usuarioSistema.getUsuario());
		}
		
		if(result.hasErrors()){
			System.out.println("erro no bean ocorrencia!!!");
			return nova(ocorrencia);
		}
		
		/*if(ocorrencia.isNovo() && listaAlunosSession.totalAlunos(ocorrencia.getUuid()) == 0){
			System.out.println("Ao menos uma aluno deve ser selecionado");
			result.rejectValue("aluno", "Ao menos uma aluno deve ser selecionado", "Ao menos uma aluno deve ser selecionado");
			return nova(ocorrencia);
		}*/
		
		cadastroOcorrenciaService.salvar(ocorrencia, usuarioSistema.getUsuario());
			
		attributs.addFlashAttribute("mensagemSucesso", "Ocorrencia salva com sucesso");
		
		/*return new ModelAndView("redirect:/ocorrencias/nova");*/
		return new ModelAndView("redirect:/ocorrencias/nova/sucesso");
	}
	
	@GetMapping(value="/nova/sucesso")
	public ModelAndView salvoComSucesso(){
		return new ModelAndView("ocorrencia/CadastroOcorrenciaSucesso");
	}
	
	@GetMapping(value="/locais", produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<String> getLocais(@RequestParam("local") String local){
		System.out.println("ocorrencias/locais ---> getLocais ");
		
		//TODO: teste apagar
		List<String> locais = ocorrencias.getLocais(local);
		System.out.println("locais: "+locais.size());
		
		return locais;
	}
	
	@GetMapping(value="/aluno/{codigoaluno}")
	public @ResponseBody List<OcorrenciaDTO> getOcorencias(@PathVariable("codigoaluno") Aluno aluno){
		List<OcorrenciaDTO> ocorrenciasDTO = new ArrayList<>();
			List<Ocorrencia> ocorrenciasComEncaminhamentos = ocorrencias.buscarComEncaminhamentos(aluno);
			
			for(Ocorrencia o : ocorrenciasComEncaminhamentos){
				ocorrenciasDTO.add(new OcorrenciaDTO(o));
			}
			
		return ocorrenciasDTO;
		/*return ocorrencias.porAluno(aluno);*/
	}
	
	@GetMapping(value="/quantidade/{codigoaluno}")
	public @ResponseBody Long getQuantidadeOcorenciasPorAluno(@PathVariable("codigoaluno") Aluno aluno){
		return ocorrencias.countByAluno(aluno);
	}
	
	@Secured(value="ROLE_NOVO_ENCAMINHAMENTO")
	@GetMapping(value="/ver/{codigoocorrencia}")
	public @ResponseBody OcorrenciaDTO getOcorrencia(@PathVariable("codigoocorrencia") Long codigo){
		Ocorrencia o = ocorrencias.porCodigo(codigo);
		System.out.println("======>>>>>>>> Ocorerncia: "+o.getCodigo());
		System.out.println("======>>>>>>>> Alunos: "+o.getAlunos().size());
		OcorrenciaDTO ocorrenciaDTO = new OcorrenciaDTO(o);
		return ocorrenciaDTO;
	}
	
	@DeleteMapping(value="/{codigos}")
	public ResponseEntity<?> excluir(@PathVariable Long[] codigos){
		
		try {
			cadastroOcorrenciaService.excluir(codigos);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Não foi possível excluir! "+e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Ocorrencia ocorrencia){
		System.out.println("codigo da ocorrencia: "+ ocorrencia.getCodigo());
		/*ocorrencia = ocorrencias.buscarComEncaminhamentosPorCodigo(ocorrencia.getCodigo());*/
		ocorrencia = ocorrencias.buscarComAlunos(ocorrencia.getCodigo());
		//TODO: teste de retorno dos alunos
		System.out.println("-----------------------> alunos registrados na ocorrencia: "+ocorrencia.getAlunos().size());
		return nova(ocorrencia);
	}
	
	@GetMapping("/detalhes/{codigo}")
	public ModelAndView verDetalhes(@PathVariable("codigo") Ocorrencia ocorrencia){
		ModelAndView mv = new ModelAndView("ocorrencia/VerDetalhesCadastroOcorrencia");
		ocorrencia = ocorrencias.buscarComAlunos(ocorrencia.getCodigo());
		mv.addObject(ocorrencia);
		mv.addObject("tipoEncaminhamento", TipoEncaminhamento.values());
		
		return mv;
	}
	
	@GetMapping("/totalPorMes")
	public @ResponseBody List<GraficoOcorrenciasPorMes> listarTotalOcorrenciasPorMes() {
		return ocorrencias.totalPorMes();
	}
}
