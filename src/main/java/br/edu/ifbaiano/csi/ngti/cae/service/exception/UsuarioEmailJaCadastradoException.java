package br.edu.ifbaiano.csi.ngti.cae.service.exception;

public class UsuarioEmailJaCadastradoException extends RuntimeException{

	private static final long serialVersionUID = -2498043077332672709L;

	public UsuarioEmailJaCadastradoException(String message) {
		super(message);
	}
}
