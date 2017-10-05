package br.edu.ifbaiano.csi.ngti.cae.model;

public class Atendimento extends Entidade {

	private static final long serialVersionUID = 1L;
	
	private Paciente paciente;

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	public String toString(){
	
		if (paciente instanceof Aluno){
			
		}
		
		return "Atendimento Nº: "+this.getCodigo() + "\n"
			+ "Codigo do paciente: "+paciente.getCodigo() + "\n"
			+ "Paciente: "+paciente.getNome() + "\n"
			+ "Matricula: "+paciente.getMatricula() + "\n"
			+ "Data de nascimento: " + paciente.getDataNascimento() + "\n"
			+ "============================================";
	}
}
