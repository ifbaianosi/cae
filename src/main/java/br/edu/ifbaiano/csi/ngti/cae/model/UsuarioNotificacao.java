package br.edu.ifbaiano.csi.ngti.cae.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="usuario_notificacao")
public class UsuarioNotificacao extends Entidade{

	private static final long serialVersionUID = 7280324549999342811L;

	@ManyToOne
	@JoinColumn(name="codigo_usuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="codigo_notificacao")
	private Notificacao notificacao;
	
	@Column(name="data_visualizacao")
	private LocalDateTime dataVisualizacao;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Notificacao getNotificacao() {
		return notificacao;
	}

	public void setNotificacao(Notificacao notificacao) {
		this.notificacao = notificacao;
	}

	public LocalDateTime getDataVisualizacao() {
		return dataVisualizacao;
	}

	public void setDataVisualizacao(LocalDateTime dataVisualizacao) {
		this.dataVisualizacao = dataVisualizacao;
	}

}
