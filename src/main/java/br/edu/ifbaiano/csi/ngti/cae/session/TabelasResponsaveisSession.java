package br.edu.ifbaiano.csi.ngti.cae.session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import br.edu.ifbaiano.csi.ngti.cae.model.Responsavel;
import br.edu.ifbaiano.csi.ngti.cae.model.ResponsavelSession;

@SessionScope
@Component
public class TabelasResponsaveisSession {

	private Set<TabelaResponsavel> tabelas = new HashSet<>();

	public void adicionarResponsavel(String uuid, List<Responsavel> responsaveis) {
		TabelaResponsavel tabela = buscarTabelaPorUuid(uuid);
		tabela.adicionarResponsavel(responsaveis);
		tabelas.add(tabela);
	}

	public void excluirResponsavel(String uuid, Responsavel responsavel) {
		TabelaResponsavel tabela = buscarTabelaPorUuid(uuid);
		tabela.excluirResponsavel(responsavel);;
	}

	public void excluirTodosOsResponsavels(String uuid) {
		TabelaResponsavel tabela = buscarTabelaPorUuid(uuid);
		tabela.excluirTodosResponsaveis();
	}
	
	public List<ResponsavelSession> getResponsaveis(String uuid) {
		return buscarTabelaPorUuid(uuid).getResponsavels();
	}
	
	public int totalResponsaveis(String uuid){
		TabelaResponsavel tabela = buscarTabelaPorUuid(uuid);
		return tabela.totalResponsaveis();
	}
	
	private TabelaResponsavel buscarTabelaPorUuid(String uuid) {
		TabelaResponsavel tabela = tabelas.stream()
				.filter(t -> t.getUuid().equals(uuid))
				.findAny()
				.orElse(new TabelaResponsavel(uuid));
		return tabela;
	}
	
}
