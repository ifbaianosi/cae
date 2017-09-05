package br.edu.ifbaiano.csi.ngti.cae.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifbaiano.csi.ngti.cae.dto.PeriodoRelatorio;
import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.security.UsuarioSistema;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

	@GetMapping("/ocorrenciasEmitidas")
	public ModelAndView relatorioOcorrenciasEmitidas(){
		ModelAndView mv = new ModelAndView("relatorio/RelatorioOcorrencias");
		mv.addObject("periodoRelatorio", new PeriodoRelatorio());
		return mv;
	}
	
	@PostMapping("/ocorrenciasEmitidas")
	public ModelAndView gerarRelatorioOcorrenciasEmitidas(PeriodoRelatorio periodoRelatorio, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		Map<String, Object> parametros = new HashMap<>();
		
		Date dataInicio = Date.from(LocalDateTime.of(periodoRelatorio.getDataInicio(), LocalTime.of(0, 0, 0))
				.atZone(ZoneId.systemDefault()).toInstant());
		Date dataFim = Date.from(LocalDateTime.of(periodoRelatorio.getDataFim(), LocalTime.of(23, 59, 59))
				.atZone(ZoneId.systemDefault()).toInstant());
		
		parametros.put("format", "pdf");
		parametros.put("DATA_INICIO", dataInicio);
		parametros.put("DATA_FIM", dataFim);
		adicionarUsuarioLogadoNoRelatorio(usuarioSistema, parametros);
		adicionarImagensAoRelatorio(parametros);
		
		return new ModelAndView("relatorio_ocorrencias", parametros);
	}
	
	@GetMapping("/medidaDisciplinar/{codigoocorrencia}")
	public ModelAndView gerarImpressaoMedidaDisciplinar(@PathVariable("codigoocorrencia") Long codigo, PeriodoRelatorio periodoRelatorio, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		Map<String, Object> parametros = new HashMap<>();
		
		
		parametros.put("format", "pdf");
		parametros.put("CODIGO_OCORRENCIA", codigo);
		adicionarUsuarioLogadoNoRelatorio(usuarioSistema, parametros);
		adicionarImagensAoRelatorio(parametros);
		
		return new ModelAndView("relatorio_imprimir_ocorrencia", parametros);
	}

	private void adicionarUsuarioLogadoNoRelatorio(UsuarioSistema usuarioSistema, Map<String, Object> parametros) {
		parametros.put("USUARIO_SISTEMA", usuarioSistema.getUsuario().getPrimeiroNomeEmail());
	}

	private void adicionarImagensAoRelatorio(Map<String, Object> parametros) {
		parametros.put("LOGO_EMPRESA", "/relatorios/img/marca-if-baiano-para-o-site.png");
		parametros.put("BRASAO_REPUBLICA", "/relatorios/img/brasao.png");
		parametros.put("LOGO_SISTEMA", "/relatorios/img/trino-logo.png");
	}
	
}
