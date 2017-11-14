package br.edu.ifbaiano.csi.ngti.cae.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.edu.ifbaiano.csi.ngti.cae.security.AppUserDetailsService;

@EnableWebSecurity
@ComponentScan(basePackageClasses = AppUserDetailsService.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/layout/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/alunos/pesquisa").hasRole("CONSULTAR_DADOS_ALUNO")
			.antMatchers("/alunos/novo").hasRole("NOVO_ALUNO")
			.antMatchers("/alunos/{\\d+}").hasRole("EDITAR_ALUNO")
			.antMatchers("/alunos/detalhe").hasRole("CONSULTAR_HISTORICO_ALUNO")
			.antMatchers("/alunos/adicionar").hasRole("NOVA_OCORRENCIA")
			.antMatchers("/alunos/por").hasRole("PESQUISAR_ALUNO_POR_NOMEOUMATRICULA")
			.antMatchers("/alunos/por-matricula").hasRole("PESQUISAR_ALUNO_POR_MATRICULA")
			.antMatchers("/alunos").hasRole("PESQUISAR_ALUNO")

			.antMatchers("/responsaveis").hasRole("PESQUISAR_RESPONSAVEIS")
			.antMatchers("/responsaveis/novo").hasRole("NOVO_RESPONSAVEL")
			.antMatchers("/responsaveis/{\\d+}").hasRole("EDITAR_RESPONSAVEL")

			.antMatchers("/perfis").hasRole("PESQUISAR_GRUPOS")
			.antMatchers("/perfis/novo").hasRole("NOVO_GRUPO")
			.antMatchers("/perfis/{\\d+}").hasRole("EDITAR_GRUPO")
			
			
			.antMatchers("/usuarios").hasRole("PESQUISAR_USUARIO")
			.antMatchers("/usuarios/novo").hasRole("NOVO_USUARIO")
			.antMatchers("/usuarios/{\\d+}").hasRole("EDITAR_USUARIO")
			
			.antMatchers("/ocorrencias/totalPorMes").hasRole("VER_GRAFICO_OCORRENCIAS_MES")
			.antMatchers("/ocorrencias").hasRole("PESQUISAR_TODAS_OCORRENCIAS")
			.antMatchers("/ocorrencias/nova").hasRole("NOVA_OCORRENCIA")
			.antMatchers("/ocorrencias/autoria").hasRole("PESQUISAR_OCORRENCIAS_PROPRIA_AUTORIA")
			.antMatchers("/ocorrencias/locais").hasRole("NOVA_OCORRENCIA")
			.antMatchers("/ocorrencias/{\\d+}").hasRole("EDITAR_OCORRENCIA")

			.antMatchers("/relatorios/ocorrenciasEmitidas").hasRole("RELATORIO_OCORRENCIAS")
			.antMatchers("/relatorios/medidaDisciplinar/{\\d+}").hasRole("IMPRIMIR_MEDIDA_DISCIPLINAR")
				
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.and()
			.exceptionHandling()
				.accessDeniedPage("/403")
				.and()
			.sessionManagement()
				.invalidSessionUrl("/login")
				/*.and()
			.csrf().disable()*/;
			
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
