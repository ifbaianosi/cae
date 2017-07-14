package br.edu.ifbaiano.csi.ngti.cae.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;
import br.edu.ifbaiano.csi.ngti.cae.model.Usuario;
import br.edu.ifbaiano.csi.ngti.cae.repository.filter.OcorrenciaFilter;
import br.edu.ifbaiano.csi.ngti.cae.repository.filter.UsuarioFilter;

public interface UsuariosQueries {

	public Page<Usuario> filtrar(UsuarioFilter usuarioFilter, Pageable pageable);
	public Optional<Usuario> porEmailEAtivo(String email);
	public List<String> permissoes(Usuario usuario);
	public Usuario buscarComGrupos(Long codigo);
}
