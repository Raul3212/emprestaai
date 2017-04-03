package br.ufc.npi.emprestaai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.emprestaai.bean.Usuario;
import br.ufc.npi.emprestaai.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public void salvarUsuario(Usuario usuario){
		usuarioRepository.save(usuario);
	}
	
	public Usuario buscarPorLogin(String login){
		return usuarioRepository.findByLogin(login);
	}
	
}
