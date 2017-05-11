package br.edu.ifbaiano.csi.ngti.cae.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifbaiano.csi.ngti.cae.model.Contato;

@Repository
public interface Contatos extends JpaRepository<Contato, Long>{

	public Optional<Contato> findByNumero(String numero);
}
