package br.edu.ifbaiano.csi.ngti.cae.session;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.edu.ifbaiano.csi.ngti.cae.model.Aluno;

public class ListaAlunosTest {

	private ListaAluno listaAluno;
	
	@Before
	public void setUp(){
		String uuid = "1";
		listaAluno = new ListaAluno(uuid);
	}
	
	@Test
	public void deveConterZeroAlunos() throws Exception {
		assertEquals(0, listaAluno.totalAlunos());
	}
	
	@Test
	public void deveAdicionarUmAluno() throws Exception {
		List<Aluno> alunos = new ArrayList<Aluno>();
		Aluno a1 = new Aluno();
		a1.setCodigo(1L);
		
		alunos.add(a1);
		
		listaAluno.adicionarAluno(alunos);
		
		assertEquals(1, listaAluno.totalAlunos());
	}
	
	@Test
	public void deveAdicionarVariosAlunos() throws Exception {
		List<Aluno> alunos = new ArrayList<>();
		Aluno a1 = new Aluno();
		a1.setCodigo(1L);
		
		Aluno a2 = new Aluno();
		a2.setCodigo(2L);
		
		Aluno a3 = new Aluno();
		a3.setCodigo(3L);

		Aluno a4 = new Aluno();
		a4.setCodigo(4L);
		
		alunos.add(a1);
		alunos.add(a2);
		alunos.add(a3);
		alunos.add(a4);
		
		listaAluno.adicionarAluno(alunos);
		
		assertEquals(4, listaAluno.totalAlunos());
	}
	
	@Test
	public void deveManterTamanhoDaListaParaMesmosAlunos() throws Exception {
		List<Aluno> alunos = new ArrayList<>();
		Aluno a1 = new Aluno();
		a1.setCodigo(1L);
		
		Aluno a2 = new Aluno();
		a2.setCodigo(2L);
		
		Aluno a3 = new Aluno();
		a3.setCodigo(3L);

		Aluno a4 = new Aluno();
		a4.setCodigo(4L);
		
		alunos.add(a1);
		alunos.add(a2);
		alunos.add(a3);
		alunos.add(a4);
		alunos.add(a1); //aluno ja adicionado deve ignorar
		alunos.add(a1); //aluno ja adicionado deve ignorar
		
		listaAluno.adicionarAluno(alunos);
		
		assertEquals(4, listaAluno.totalAlunos());
	}
	
	@Test
	public void deveExcluirAlunos() throws Exception {
		List<Aluno> alunos = new ArrayList<>();
		Aluno a1 = new Aluno();
		a1.setCodigo(1L);
		
		Aluno a2 = new Aluno();
		a2.setCodigo(2L);
		
		Aluno a3 = new Aluno();
		a3.setCodigo(3L);

		Aluno a4 = new Aluno();
		a4.setCodigo(4L);
		
		alunos.add(a1);
		alunos.add(a2);
		alunos.add(a3);
		alunos.add(a4);
		
		listaAluno.adicionarAluno(alunos);
		listaAluno.excluirAluno(a2);
		
		assertEquals(3, listaAluno.totalAlunos());

		listaAluno.excluirAluno(a3);
		
		assertEquals(2, listaAluno.totalAlunos());
		
		listaAluno.excluirTodosAlunos();
		
		assertEquals(0, listaAluno.totalAlunos());
	}
}
