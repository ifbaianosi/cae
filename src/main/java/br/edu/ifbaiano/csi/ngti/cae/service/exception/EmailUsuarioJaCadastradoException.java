package br.edu.ifbaiano.csi.ngti.cae.service.exception;

public class EmailUsuarioJaCadastradoException extends RuntimeException{

	private static final long serialVersionUID = -2498043077332672709L;

	public EmailUsuarioJaCadastradoException(String message) {
		super(message);
	}
}
