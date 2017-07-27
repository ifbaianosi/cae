package br.edu.ifbaiano.csi.ngti.cae.model;

import org.hibernate.validator.constraints.NotBlank;

public class SenhaUsuario {

	/*@NotBlank(message="Informe a senha atual")*/
	private String senhaAtual;
	
	@NotBlank(message="Informe a nova senha")
	private String senha;
	
	private String confirmacaoSenha;

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}
	
	public boolean isValid(String senha, String confirmacao){
		return ambosSaoNull(senha, confirmacao) || ambosSaoIguais(senha, confirmacao);
	}
	
	private boolean ambosSaoIguais(String valorAtributo, String valorAtributoConfirmacao) {
		return !valorAtributo.equals("") && valorAtributo.equals(valorAtributoConfirmacao);
	}

	private boolean ambosSaoNull(String valorAtributo, String valorAtributoConfirmacao) {
		return valorAtributo.equals("") && valorAtributoConfirmacao.equals("");
	}
	
}
