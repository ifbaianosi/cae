package br.edu.ifbaiano.csi.ngti.cae.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifbaiano.csi.ngti.cae.model.Responsavel;

@Repository
public interface Responsaveis extends JpaRepository<Responsavel, Long> {

	public Optional<Responsavel> findByNomeIgnoreCaseAndContato(String nome, String contato);

	public Optional<Responsavel> findByContato(String contato);
	public List<Responsavel> findByNomeStartsWithIgnoreCase(String nome);
}
