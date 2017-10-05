package br.edu.ifbaiano.csi.ngti.cae.repository.helper.notificacao;

import java.util.List;

import br.edu.ifbaiano.csi.ngti.cae.dto.NotificacaoDTO;
import br.edu.ifbaiano.csi.ngti.cae.model.TipoNotificacao;


public interface NotificacoesQueries {

	public List<NotificacaoDTO> findByCodigoNotIn(List<Long> codigos);
	public List<NotificacaoDTO> findByCodigoNotInAndTipo(List<Long> codigos);
	public List<NotificacaoDTO> all();
	public Long quantidadeNotificacoes(List<Long> codigos);
	public List<NotificacaoDTO> findByTipo(TipoNotificacao tipo);//sem uso

}
