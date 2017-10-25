package br.edu.ifbaiano.csi.ngti.cae.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifbaiano.csi.ngti.cae.model.Grupo;
import br.edu.ifbaiano.csi.ngti.cae.repository.helper.grupo.GruposQueries;

@Repository
public interface Grupos extends JpaRepository<Grupo, Long>, GruposQueries{

	public Optional<Grupo> findByNomeIgnoreCase(String nome);
}
