package br.edu.ifbaiano.csi.ngti.cae.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.edu.ifbaiano.csi.ngti.cae.controller.AlunosController;
import br.edu.ifbaiano.csi.ngti.cae.service.CadastroAlunoService;
import br.edu.ifbaiano.csi.ngti.cae.session.TabelasResponsaveisSession;

@Configuration
@ComponentScan(basePackageClasses = { CadastroAlunoService.class, TabelasResponsaveisSession.class })
public class ServiceConfig {

}
