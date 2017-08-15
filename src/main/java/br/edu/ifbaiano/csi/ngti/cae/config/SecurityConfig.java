package br.edu.ifbaiano.csi.ngti.cae.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
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
			.antMatchers("/alunos").hasRole("PESQUISAR_ALUNO")
			.antMatchers("/alunos/novo").hasRole("NOVO_ALUNO")
			.antMatchers("/alunos/detalhe").hasRole("CONSULTAR_HISTORICO_ALUNO")
			.antMatchers("/alunos/adicionar").hasRole("CONSULTAR_DADOS_ALUNO") // configurar a permissão de forma correta para essa url
			
			.antMatchers("/alunos/por").hasRole("CONSULTAR_DADOS_ALUNO")
			.antMatchers("/alunos/por-matricula").hasRole("CONSULTAR_DADOS_ALUNO")
			.antMatchers("/alunos/pesquisa").hasRole("CONSULTAR_DADOS_ALUNO")
			
			.antMatchers("/usuarios").hasRole("PESQUISAR_USUARIO")
			.antMatchers("/usuarios/novo").hasRole("NOVO_USUARIO")
			
			.antMatchers("/ocorrencias").hasRole("PESQUISAR_OCORRENCIA")
			.antMatchers("/ocorrencias/nova").hasRole("NOVA_OCORRENCIA")
			.antMatchers("/ocorrencias/autoria").hasRole("PESQUISAR_OCORRENCIAS_PROPRIA_AUTORIA")
			.antMatchers("/ocorrencias/locais").hasRole("NOVA_OCORRENCIA")
			.antMatchers("/ocorrencias/{\\d+}").hasRole("EDITAR_OCORRENCIA")
				
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
