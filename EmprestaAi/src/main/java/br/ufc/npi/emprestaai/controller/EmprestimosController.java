package br.ufc.npi.emprestaai.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.ufc.npi.emprestaai.bean.Contato;
import br.ufc.npi.emprestaai.bean.Item;
import br.ufc.npi.emprestaai.bean.Usuario;
import br.ufc.npi.emprestaai.service.ContatoService;
import br.ufc.npi.emprestaai.service.ItemService;

@Controller
@RequestMapping(path="/emprestimos/")
public class EmprestimosController {

	@Autowired
	ContatoService contatoService;
	
	@Autowired
	ItemService itemService;
	
	@RequestMapping(path="/")
	public String index(HttpSession session, Model model){
		Usuario usuarioLogado = (Usuario)session.getAttribute("usuario");
		if(usuarioLogado != null){
			List<Contato> todoscontatos = contatoService.listByUsuarioId(usuarioLogado.getId());
			List<Contato> contatosComEmprestimo = new ArrayList<>();
			for(Contato c : todoscontatos){
				if(c.getItens().size() > 0){
					contatosComEmprestimo.add(c);
				}
			}
			model.addAttribute("contatos", contatosComEmprestimo);
			return "emprestimos/index";
		}else{
			return "redirect:/";
		}
	}
	
	@RequestMapping(path="/cadastro")
	public String cadastroEmprestimo(HttpSession session, Model model){
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");
		if(usuarioLogado != null){
			List<Contato> contatos = contatoService.listByUsuarioId(usuarioLogado.getId());
			List<Item> itens = itemService.findDisponibleItens(usuarioLogado.getId());
			model.addAttribute("contatos", contatos);
			model.addAttribute("itens", itens);
			return "emprestimos/cadastro";
		}else{
			return "redirect:/";
		}
	}
	
	@RequestMapping(path="/cadastrar", method=RequestMethod.GET)
	public String castrarEmprestimo(Long contatoId, Long itemId, Date dataDevolucao){
		Item item = itemService.getById(itemId);
		Contato contato = contatoService.getById(contatoId);
		item.setContato(contato);
		item.setDataDevolucao(dataDevolucao);
		itemService.updateItem(item);
		return "redirect:/emprestimos/";
	}
	
	@RequestMapping(path="/devolver/{itemId}")
	public String devolver(@PathVariable("itemId")Long itemId){
		Item item = itemService.getById(itemId);
		item.setContato(null);
		item.setDataDevolucao(null);
		itemService.updateItem(item);
		return "redirect:/emprestimos/";
	}
	
}
