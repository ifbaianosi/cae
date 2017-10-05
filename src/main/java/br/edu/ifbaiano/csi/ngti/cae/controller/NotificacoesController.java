package br.edu.ifbaiano.csi.ngti.cae.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifbaiano.csi.ngti.cae.dto.NotificacaoDTO;
import br.edu.ifbaiano.csi.ngti.cae.model.Notificacao;
import br.edu.ifbaiano.csi.ngti.cae.model.UsuarioNotificacao;
import br.edu.ifbaiano.csi.ngti.cae.repository.Notificacoes;
import br.edu.ifbaiano.csi.ngti.cae.repository.UsuarioNotificacoes;
import br.edu.ifbaiano.csi.ngti.cae.security.UsuarioSistema;
import br.edu.ifbaiano.csi.ngti.cae.service.CadastroUsuarioNotificacaoService;

@Controller
@RequestMapping("/notificacoes")
public class NotificacoesController {
	
	@Autowired
	private Notificacoes notificacoes;
	
	@Autowired
	private UsuarioNotificacoes usuarioNotificacoes;
	
	@Autowired
	private CadastroUsuarioNotificacaoService cadastroUsuarioNotificacaoService;
	
	@GetMapping
	public @ResponseBody ResponseEntity<?> notificacoes(@AuthenticationPrincipal UsuarioSistema usuarioSistema){
		List<Long> codigos = usuarioNotificacoes.findByUsuario(usuarioSistema.getUsuario());
		List<NotificacaoDTO> ntfs = new ArrayList<>();
		try {
			ntfs = notificacoes.findByCodigoNotIn(codigos);
		} catch (Exception e) {
			return ResponseEntity.ok(notificacoes.all());
		}
		
		return ResponseEntity.ok(ntfs);
	}
	
	/*@GetMapping("/encaminhamentos")
	public @ResponseBody ResponseEntity<?> notificacoesEncaminhamentos(@AuthenticationPrincipal UsuarioSistema usuarioSistema){
		List<Long> codigos = usuarioNotificacoes.findByUsuario(usuarioSistema.getUsuario());
		List<NotificacaoDTO> ntfs = new ArrayList<>();
		try {
			ntfs = notificacoes.findByCodigoNotInAndTipo(codigos);
		} catch (Exception e) {
			return ResponseEntity.ok(notificacoes.findByTipo(TipoNotificacao.NOVO_ENCAMINHAMENTO));
		}
		
		return ResponseEntity.ok(ntfs);
	}*/
	
	@GetMapping("/{codigo}")
	public ModelAndView redirecionar(@PathVariable("codigo") Notificacao notificacao, @AuthenticationPrincipal UsuarioSistema usuarioSistema){
		UsuarioNotificacao usuarioNotificacao = new UsuarioNotificacao();
		
		usuarioNotificacao.setDataVisualizacao(LocalDateTime.now());
		usuarioNotificacao.setNotificacao(notificacao);
		usuarioNotificacao.setUsuario(usuarioSistema.getUsuario());
		
		//Marca a notificação como visualizada pelo usuario logado
		cadastroUsuarioNotificacaoService.salvar(usuarioNotificacao);
		
		return new ModelAndView("redirect:/"+notificacao.getUri());
	}
}
