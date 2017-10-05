package br.edu.ifbaiano.csi.ngti.cae.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifbaiano.csi.ngti.cae.dto.NotificacaoDTO;
import br.edu.ifbaiano.csi.ngti.cae.model.Notificacao;
import br.edu.ifbaiano.csi.ngti.cae.model.TipoNotificacao;
import br.edu.ifbaiano.csi.ngti.cae.repository.helper.notificacao.NotificacoesQueries;

public interface Notificacoes extends JpaRepository<Notificacao, Long>, NotificacoesQueries{
}
