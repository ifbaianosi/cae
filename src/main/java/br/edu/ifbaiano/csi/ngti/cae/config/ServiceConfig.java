package br.edu.ifbaiano.csi.ngti.cae.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.edu.ifbaiano.csi.ngti.cae.service.CadastroAlunoService;

@Configuration
@ComponentScan(basePackageClasses= CadastroAlunoService.class)
public class ServiceConfig {

}
