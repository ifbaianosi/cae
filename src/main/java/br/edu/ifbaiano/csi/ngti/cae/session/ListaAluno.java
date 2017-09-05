package br.edu.ifbaiano.csi.ngti.cae.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;

public class ListaAluno {

	private String uuid;
	private List<Aluno> listaAlunosSession = new ArrayList<Aluno>();
	
	public ListaAluno() {}
	
	public ListaAluno(String uuid) {
		this.uuid = uuid;
	}
	
	public void adicionarAluno(List<Aluno> alunos){
		for(Aluno aluno : alunos){
			Optional<Aluno> alunoOptional = buscarPorAluno(aluno);
			if (!alunoOptional.isPresent()){
				listaAlunosSession.add(0, aluno);
			}
		}
	}
	
	public void excluirAluno(Aluno aluno){
		int indice = IntStream.range(0, listaAlunosSession.size())
				.filter(i -> listaAlunosSession.get(i).getCodigo().equals(aluno.getCodigo()))
				.findAny().getAsInt();
		
		listaAlunosSession.remove(indice);
	}
	
	public void excluirTodosAlunos(){
		listaAlunosSession.clear();
	}
	
	public int totalAlunos(){
		return listaAlunosSession.size();
	}
	
	public List<Aluno> getAlunos(){
		return listaAlunosSession;
	}
	
	public Optional<Aluno> buscarPorAluno(Aluno aluno){
		return listaAlunosSession.stream()
				.filter(i -> i.getCodigo() == (aluno.getCodigo()))
				.findAny();
	}
	
	public String getUuid() {
		return uuid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListaAluno other = (ListaAluno) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
	
	
}
