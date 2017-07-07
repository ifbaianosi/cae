package br.edu.ifbaiano.csi.ngti.cae.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import br.edu.ifbaiano.csi.ngti.cae.model.Usuario;

public interface UsuariosQueries {

	public Optional<Usuario> porEmailEAtivo(String email);
	public List<String> permissoes(Usuario usuario);
}
