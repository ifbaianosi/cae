package br.edu.ifbaiano.csi.ngti.cae.session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;

@SessionScope
@Component
public class ListaAlunosSession {

	private Set<ListaAluno> listasDeAlunos = new HashSet<ListaAluno>();

	public void adicionarAluno(String uuid, List<Aluno> alunos) {
		ListaAluno lista = getListaPorUuid(uuid);
		lista.adicionarAluno(alunos);
		listasDeAlunos.add(lista);
	}

	public void excluirAluno(String uuid, Aluno aluno) {
		ListaAluno lista = getListaPorUuid(uuid);
		lista.excluirAluno(aluno);
	}

	public void excluirTodosOsAlunos(String uuid) {
		ListaAluno lista = getListaPorUuid(uuid);
		lista.excluirTodosAlunos();
	}
	
	public List<Aluno> getAlunos(String uuid) {
		return getListaPorUuid(uuid).getAlunos();
	}
	
	public int totalAlunos(String uuid){
		ListaAluno lista = getListaPorUuid(uuid);
		return lista.totalAlunos();
	}
	
	private ListaAluno getListaPorUuid(String uuid) {
		ListaAluno lista = listasDeAlunos.stream()
				.filter(t -> t.getUuid().equals(uuid))
				.findAny()
				.orElse(new ListaAluno(uuid));
		return lista;
	}
	
}
