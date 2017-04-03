package br.ufc.npi.emprestaai.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufc.npi.emprestaai.bean.Item;
import br.ufc.npi.emprestaai.bean.Usuario;
import br.ufc.npi.emprestaai.service.ItemService;

@Controller
@RequestMapping(path="/itens/")
public class ItemController {

	@Autowired
	ItemService itemService;
	
	@RequestMapping(path="/")
	public String index(HttpSession session, Model model){
		Usuario usuarioLogado = (Usuario)session.getAttribute("usuario");
		if(usuarioLogado != null){
			List<Item> itens = itemService.listByUsuarioId(usuarioLogado.getId());
			model.addAttribute("itens", itens);
			return "itens/index";
		}else{
			return "redirect:/";
		}
	}
	
	@RequestMapping(path="/cadastro")
	public String paginaCadastroItens(){
		return "itens/cadastro";
	}
	
	@RequestMapping(path="/cadastrar")
	public String cadastrarItem(Item item, HttpSession session){
		Usuario usuarioLogado = (Usuario)session.getAttribute("usuario");
		if(usuarioLogado != null){
			item.setUsuario(usuarioLogado);
			itemService.saveItem(item);
			return "redirect:/itens/";
		}else{
			return "redirect:/";
		}
	}
	
	@RequestMapping(path="/{id}")
	public String detalharItem(@PathVariable("id")Long id, Model model){
		Item item = itemService.getById(id);
		model.addAttribute("item", item);
		return "/itens/detalhes";
	}
	
	@RequestMapping(path="/deletar/{id}")
	public String deletarItem(@PathVariable("id")Long id){
		itemService.deleteItem(id);
		return "redirect:/itens/";
	}
	
}
