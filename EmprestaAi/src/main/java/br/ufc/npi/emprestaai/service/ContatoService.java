package br.ufc.npi.emprestaai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.emprestaai.bean.Contato;
import br.ufc.npi.emprestaai.bean.Usuario;
import br.ufc.npi.emprestaai.repository.ContatoRepository;

@Service
public class ContatoService {
	
	@Autowired
	ContatoRepository contatoRepository;
	
	public Contato getById(Long id){
		return contatoRepository.findOne(id);
	}
	
	public List<Contato> listAll(){
		return contatoRepository.findAll(); 
	}
	
	public void saveContato(Contato contato){
		contatoRepository.save(contato);
	}
	
	public List<Contato> listByUsuarioId(Long usuarioId){
		return contatoRepository.findByUsuarioId(usuarioId);
	}
	
	public void deleteContato(Long contatoId){
		Contato contato = new Contato();
		contato.setId(contatoId);
		contatoRepository.delete(contato);
	}
	
}
