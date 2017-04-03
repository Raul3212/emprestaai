package br.ufc.npi.emprestaai.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufc.npi.emprestaai.bean.Usuario;
import br.ufc.npi.emprestaai.service.UsuarioService;

@Controller
public class MainController {

	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(path="/")
	public String index(){
		return "index";
	}
	
	@RequestMapping(path="formLogin")
	public String formLogin(){
		return "login";
	}
	
	@RequestMapping(path="/login")
	public String login(String login, String senha, HttpSession session){
		Usuario usuario = usuarioService.buscarPorLogin(login);
		if(usuario != null){
			if(usuario.getSenha().equals(senha)){
				session.setAttribute("usuario", usuario);
				return "home";
			}
		}
		return "redirect:formLogin";
		
	}
	
	@RequestMapping(path="/home")
	public String home(HttpSession session){
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		if(usuario != null){
			return "home";
		}
		return "redirect:/";
	}
	
	@RequestMapping(path="/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(path="/cadastro")
	public String cadastro(){
		return "cadastro";
	}
	
	@RequestMapping(path="/cadastrar")
	public String cadastrar(Usuario usuario){
		usuarioService.salvarUsuario(usuario);
		return "redirect:/";
	}
	
}
