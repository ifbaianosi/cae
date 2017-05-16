package br.edu.ifbaiano.csi.ngti.cae.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import br.edu.ifbaiano.csi.ngti.cae.model.Responsavel;
import br.edu.ifbaiano.csi.ngti.cae.model.ResponsavelSession;

public class TabelaResponsavel {

	private String uuid;
	private List<ResponsavelSession> responsavelSession = new ArrayList<ResponsavelSession>();
	
	public TabelaResponsavel() {}
	
	public TabelaResponsavel(String uuid) {
		this.uuid = uuid;
	}
	
	public void adicionarResponsavel(List<Responsavel> responsaveis){
		for(Responsavel responsavel : responsaveis){
			
			Optional<ResponsavelSession> responsavelSessionOptional = buscarPorResponsavel(responsavel);
			ResponsavelSession responsavelSesseion = null;
			
			if (!responsavelSessionOptional.isPresent()){
				responsavelSesseion = new ResponsavelSession();
				responsavelSesseion.setResponsavel(responsavel);
				responsavelSession.add(0, responsavelSesseion);
			}
			
		}
	}
	
	public void excluirResponsavel(Responsavel responsavel){
		int indice = IntStream.range(0, responsavelSession.size())
				.filter(i -> responsavelSession.get(i).getResponsavel().equals(responsavel))
				.findAny().getAsInt();
		
		responsavelSession.remove(indice);
	}
	
	public void excluirTodosResponsaveis(){
		responsavelSession.clear();
	}
	
	public int totalResponsaveis(){
		return responsavelSession.size();
	}
	
	public List<ResponsavelSession> getResponsavels(){
		return responsavelSession;
	}
	
	public Optional<ResponsavelSession> buscarPorResponsavel(Responsavel responsavel){
		return responsavelSession.stream()
				.filter(i -> i.getResponsavel().equals(responsavel))
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
		TabelaResponsavel other = (TabelaResponsavel) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
	
	
}
