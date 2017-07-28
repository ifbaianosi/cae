package br.edu.ifbaiano.csi.ngti.cae.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.edu.ifbaiano.csi.ngti.cae.model.Usuario;
import br.edu.ifbaiano.csi.ngti.cae.repository.Usuarios;
import br.edu.ifbaiano.csi.ngti.cae.service.exception.EmailUsuarioJaCadastradoException;
import br.edu.ifbaiano.csi.ngti.cae.service.exception.ImpossivelExcluirEntidadeException;
import br.edu.ifbaiano.csi.ngti.cae.service.exception.SenhaObrigatoriaUsuarioException;

@Service
public class CadastroUsuarioService {

	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public void salvar(Usuario usuario) {
		Optional<Usuario> usuarioExistente = usuarios.findByEmailIgnoreCase(usuario.getEmail());
		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new EmailUsuarioJaCadastradoException("E-mail já cadastrado");
		}
		
		if (usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
			throw new SenhaObrigatoriaUsuarioException("Senha é obrigatória para novo usuário");
		}
		
		if (usuario.isNovo() || !StringUtils.isEmpty(usuario.getSenha())) {//novo usuario ou senha preenchida
			usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));//encodar a senha para salvar no banco de dados
		} else if (StringUtils.isEmpty(usuario.getSenha())) {//se o usuario não conter uma senha ou for uma edição de usuario existente
			usuario.setSenha(usuarios.getOne(usuario.getCodigo()).getSenha());
		}
		usuario.setConfirmacaoSenha(usuario.getSenha());
		
		/*if (!usuario.isNovo() && usuario.getAtivo() == null) {
			usuario.setAtivo(usuarioExistente.get().getAtivo());
		}*/
		
		usuarios.save(usuario);
	}
	
	@Transactional
	public void alterarStatus(Long[] codigos, StatusUsuario statusUsuario) {
		statusUsuario.executar(codigos, usuarios);
	}
	
	@Transactional
	public void excluir(Usuario usuario) {
		try {
			usuarios.delete(usuario);
			usuarios.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Desculpe, mas não foi possivel excluir o usuario.");
		}
	}


	@Transactional
	public void trocarSenha(Usuario usuario) {
		usuarios.save(usuario);
	}
	
	
	
	/*
	@Transactional
	public void salvar(Usuario usuario){
		//VERIFICA SE É UM NOVO REGISTRO OU EDIÇÃO
		if(usuario.isNovo()){
			//verifica se já existe um usuario cadastrado com o email informado
			Optional<Usuario> usuarioOptional = usuarios.findByEmailIgnoreCase(usuario.getEmail());
			if(usuarioOptional.isPresent())
				throw new UsuarioEmailJaCadastradoException("Já existe um usuario cadastrado com o e-mail informado");
		}
		
		if(usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())){
			throw new SenhaObrigatoriaUsuarioException("A senha é obrigatória para novo usuário");
		}
		
		if(usuario.isNovo()){
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			usuario.setConfirmacaoSenha(usuario.getSenha());
		}
		
		//Salvar o usuario...
		usuarios.save(usuario);
	}*/



}
