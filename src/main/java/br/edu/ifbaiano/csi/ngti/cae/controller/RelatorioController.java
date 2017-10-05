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

import br.edu.ifbaiano.csi.ngti.cae.dto.RelatorioOcorrencias;
import br.edu.ifbaiano.csi.ngti.cae.security.UsuarioSistema;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

	@GetMapping("/ocorrenciasEmitidas")
	public ModelAndView relatorioOcorrenciasEmitidas(){
		ModelAndView mv = new ModelAndView("relatorio/RelatorioOcorrencias");
		mv.addObject("relatorioOcorrencias", new RelatorioOcorrencias());
		return mv;
	}
	
	@PostMapping("/ocorrenciasEmitidas")
	public ModelAndView gerarRelatorioOcorrenciasEmitidas(RelatorioOcorrencias relatorioOcorrencias, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		Map<String, Object> parametros = new HashMap<>();
		
		Date dataInicio = Date.from(LocalDateTime.of(relatorioOcorrencias.getDataInicio(), LocalTime.of(0, 0, 0))
				.atZone(ZoneId.systemDefault()).toInstant());
		Date dataFim = Date.from(LocalDateTime.of(relatorioOcorrencias.getDataFim(), LocalTime.of(23, 59, 59))
				.atZone(ZoneId.systemDefault()).toInstant());
		
		parametros.put("format", "pdf");
		parametros.put("DATA_INICIO", dataInicio);
		parametros.put("DATA_FIM", dataFim);
		adicionarUsuarioLogadoNoRelatorio(usuarioSistema, parametros);
		adicionarImagensAoRelatorio(parametros);
		
		//verifica se o usuario selecionou um aluno para gerar o relatorio contendo apenas as ocorrencias do aluno selecionado
		if(relatorioOcorrencias.getAluno().getCodigo() != null){
			System.out.println("codigo do aluno: " + relatorioOcorrencias.getAluno().getCodigo());
			parametros.put("CODIGO_ALUNO", relatorioOcorrencias.getAluno().getCodigo());
			return new ModelAndView("relatorio_ocorrencias_por_aluno", parametros);
		}
		
		return new ModelAndView("relatorio_ocorrencias", parametros);
	}

	
	@GetMapping("/medidaDisciplinar/{codigoocorrencia}")
	public ModelAndView gerarImpressaoMedidaDisciplinar(@PathVariable("codigoocorrencia") Long codigo, RelatorioOcorrencias periodoRelatorio, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
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
