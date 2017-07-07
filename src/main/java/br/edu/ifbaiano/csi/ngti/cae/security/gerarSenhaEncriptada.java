package br.edu.ifbaiano.csi.ngti.cae.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class gerarSenhaEncriptada {

	@Autowired
	private static PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		System.out.println(passwordEncoder.encode("#ngti@si$$"));
	}

}
