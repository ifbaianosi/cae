package br.edu.ifbaiano.csi.ngti.cae.model;

public enum TipoNotificacao {

	NOVA_OCORRENCIA("Nova ocorrência"),
	ATUALIZACAO_OCORRENCIA("Ocorrência atualizada"),
	NOVO_ENCAMINHAMENTO("Novo encaminhamento"),
	ATUALIZACAO_ENCAMINHAMENTO("Encaminhamento atualizado"),
	NOVO_ALUNO("Novo aluno"),
	ATUALIZACAO_ALUNO("aluno atualizado");
	
	private String descricao;
	
	private TipoNotificacao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}		
}
