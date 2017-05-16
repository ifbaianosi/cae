package br.edu.ifbaiano.csi.ngti.cae.session;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.edu.ifbaiano.csi.ngti.cae.model.Responsavel;

public class TabelaResponsaveisTest {

	private TabelaResponsavel tabelaResponsavel;
	
	@Before
	public void setUp(){
		tabelaResponsavel = new TabelaResponsavel("1");
	}
	
	@Test
	public void deveConterZeroResponsaveis() throws Exception {
		assertEquals(0, tabelaResponsavel.totalResponsaveis());
	}
	
	@Test
	public void deveAdicionarUmResponsavel() throws Exception {
		List<Responsavel> contatos = new ArrayList<Responsavel>();
		Responsavel a1 = new Responsavel();
		a1.setCodigo(1L);
		
		contatos.add(a1);
		
		tabelaResponsavel.adicionarResponsavel(contatos);
		
		assertEquals(1, tabelaResponsavel.totalResponsaveis());
	}
	
	@Test
	public void deveAdicionarVariosResponsaveis() throws Exception {
		List<Responsavel> contatos = new ArrayList<>();
		Responsavel a1 = new Responsavel();
		a1.setCodigo(1L);
		
		Responsavel a2 = new Responsavel();
		a2.setCodigo(2L);
		
		Responsavel a3 = new Responsavel();
		a3.setCodigo(3L);

		Responsavel a4 = new Responsavel();
		a4.setCodigo(4L);
		
		contatos.add(a1);
		contatos.add(a2);
		contatos.add(a3);
		contatos.add(a4);
		
		tabelaResponsavel.adicionarResponsavel(contatos);
		
		assertEquals(4, tabelaResponsavel.totalResponsaveis());
	}
	
	@Test
	public void deveManterTamanhoDaListaParaMesmosResponsaveis() throws Exception {
		List<Responsavel> contatos = new ArrayList<>();
		Responsavel a1 = new Responsavel();
		a1.setCodigo(1L);
		
		Responsavel a2 = new Responsavel();
		a2.setCodigo(2L);
		
		Responsavel a3 = new Responsavel();
		a3.setCodigo(3L);

		Responsavel a4 = new Responsavel();
		a4.setCodigo(4L);
		
		contatos.add(a1);
		contatos.add(a2);
		contatos.add(a3);
		contatos.add(a4);
		contatos.add(a1);//aluno ja adicionado deve ignorar
		
		tabelaResponsavel.adicionarResponsavel(contatos);
		
		assertEquals(4, tabelaResponsavel.totalResponsaveis());
	}
	
	@Test
	public void deveExcluirResponsaveis() throws Exception {
		List<Responsavel> contatos = new ArrayList<>();
		Responsavel a1 = new Responsavel();
		a1.setCodigo(1L);
		
		Responsavel a2 = new Responsavel();
		a2.setCodigo(2L);
		
		Responsavel a3 = new Responsavel();
		a3.setCodigo(3L);

		Responsavel a4 = new Responsavel();
		a4.setCodigo(4L);
		
		contatos.add(a1);
		contatos.add(a2);
		contatos.add(a3);
		contatos.add(a4);
		
		tabelaResponsavel.adicionarResponsavel(contatos);
		tabelaResponsavel.excluirResponsavel(a2);
		
		assertEquals(3, tabelaResponsavel.totalResponsaveis());

		tabelaResponsavel.excluirResponsavel(a3);
		
		assertEquals(2, tabelaResponsavel.totalResponsaveis());
	}
}
