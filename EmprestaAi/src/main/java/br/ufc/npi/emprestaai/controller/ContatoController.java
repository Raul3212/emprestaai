package br.ufc.npi.emprestaai.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufc.npi.emprestaai.bean.Contato;
import br.ufc.npi.emprestaai.bean.Usuario;
import br.ufc.npi.emprestaai.service.ContatoService;

@Controller
@RequestMapping(path="/contatos/")
public class ContatoController {
	
	@Autowired
	ContatoService contatoService;
	
	@RequestMapping(path="/")
	public String paginaContatos(HttpSession session, Model model){
		Usuario usuarioLogado = (Usuario)session.getAttribute("usuario");
		if(usuarioLogado != null){
			usuarioLogado.setContatos(contatoService.listByUsuarioId(usuarioLogado.getId()));
			model.addAttribute("contatos", usuarioLogado.getContatos());
			return "contatos/index";
		}else{
			return "redirect:/";
		}
	}
	
	@RequestMapping(path="/{id}")
	public String detalhesContato(@PathVariable("id") Long id, Model model){
		Contato contato = contatoService.getById(id);
		model.addAttribute("contato", contato);
		return "contatos/detalhes";
	}
	
	@RequestMapping(path="/deletar/{id}")
	public String deletarContato(@PathVariable("id") Long id){
		contatoService.deleteContato(id);
		return "redirect:/contatos/";
	}
	
	@RequestMapping(path="/cadastro")
	public String paginaCadastroContatos(){
		return "contatos/cadastro";
	}
	
	@RequestMapping(path="/cadastrar")
	public String cadastrarContato(Contato contato, HttpSession session){
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");
		if(usuarioLogado!=null){
			contato.setUsuario(usuarioLogado);
		}
		contatoService.saveContato(contato);
		return "redirect:/contatos/";
	}
	
}
