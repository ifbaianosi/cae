package br.edu.ifbaiano.csi.ngti.cae.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.edu.ifbaiano.csi.ngti.cae.model.Usuario;
import br.edu.ifbaiano.csi.ngti.cae.repository.Usuarios;
import br.edu.ifbaiano.csi.ngti.cae.service.exception.SenhaObrigatoriaUsuarioException;
import br.edu.ifbaiano.csi.ngti.cae.service.exception.UsuarioEmailJaCadastradoException;

@Service
public class CadastroUsuarioService {

	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
	}

	/*@Transactional
	public void excluir(Usuario usuario) {
		try {
			usuarios.delete(usuario);
			usuarios.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar usuario. Já existe matrículas efetuadas.");
		}
	}*/
}
