package br.edu.ifbaiano.csi.ngti.cae.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TesteAtendimentoComPaciente {

	private static List<Atendimento> atendimentos = new ArrayList<>();

	public static void main(String[] args) {
		
		Aluno aluno = new Aluno();
		aluno.setCodigo(125L);
		aluno.setNome("ADNA RIBEIRO DOS SANTOS");
		aluno.setDataNascimento(LocalDate.now());
		aluno.setMatricula("15120");
		
		//PACIENTE PODE SER ALUNO
		Paciente paciente = aluno;
		
		//ATENDIMENTO 1
		Atendimento atendimento = new Atendimento();
		atendimento.setCodigo(1L);
		atendimento.setPaciente(paciente);
		
		
		Funcionario funcionario = new Funcionario();
		funcionario.setCodigo(75L);
		funcionario.setNome("GLAUBER DE OLIVEIRA MATOS");
		funcionario.setDataNascimento(LocalDate.now());
		funcionario.setMatricula("2035281");

		//PACIENTE PODE SER FUNCIONARIO
		Paciente paciente2 = funcionario;
		
		//ATENDIMENTO 2
		Atendimento atendimento2 = new Atendimento();
		atendimento2.setCodigo(13L);
		atendimento2.setPaciente(paciente2);
		
		//LISTA DE ATENDIMENTOS
		atendimentos.add(atendimento);
		atendimentos.add(atendimento2);
		
		for(Atendimento a : atendimentos){
			System.out.println(a);
		}
	}

}
