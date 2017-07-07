package br.edu.ifbaiano.csi.ngti.cae.service.exception;

public class SenhaObrigatoriaUsuarioException extends RuntimeException {

	private static final long serialVersionUID = 7851682108714318677L;

	public SenhaObrigatoriaUsuarioException(String message) {
		super(message);
	}
}
