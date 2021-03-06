package br.ufc.npi.emprestaai.controller;

import static org.mockito.Matchers.contains;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.npi.emprestaai.bean.Contato;
import br.ufc.npi.emprestaai.bean.Item;
import br.ufc.npi.emprestaai.bean.Usuario;
import br.ufc.npi.emprestaai.service.ContatoService;
import br.ufc.npi.emprestaai.service.ItemService;
import br.ufc.quixada.npi.model.Email;
import br.ufc.quixada.npi.model.Email.EmailBuilder;
import br.ufc.quixada.npi.service.SendEmailService;

@Controller
@RequestMapping(path="/emprestimos/")
public class EmprestimosController {

	@Autowired
	private ContatoService contatoService;

	@Autowired
	private SendEmailService emailService;

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
	public String castrarEmprestimo(HttpSession session, RedirectAttributes redAttrs, Long contatoId, Long itemId, Date dataDevolucao){

		int qtdItensEmprestados = itemService.countItensEmprestados();
		if(qtdItensEmprestados < 10){

			Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");
			Item item = itemService.getById(itemId);
			Contato contato = contatoService.getById(contatoId);
			item.setContato(contato);
			item.setDataDevolucao(dataDevolucao);
			itemService.updateItem(item);

			//Enviando e-mail para usuário
			String content = 
					">> Novo empréstimo de:\n"
							+ "Item: " + item.getNome() + "\n"
							+ "Descrição: " + item.getDescricao() + "\n\n"
							+ ">> Ao contato:\n"
							+ "Nome: " + contato.getNome() + "\n"
							+ "Endereço: " + contato.getEndereco() + "\n"
							+ "Telefone: " + contato.getTelefone() + "\n\n"
							+ "DATA DE DEVOLUÇÃO: " + item.getDataDevolucao().toString();

			EmailBuilder emailBuilder = new EmailBuilder(usuarioLogado.getNome(), 
					usuarioLogado.getEmail(), 
					"Novo empréstimo de " + contato.getNome(), 
					usuarioLogado.getEmail(), 
					content);

			Email email = new Email(emailBuilder);

			emailService.sendEmail(email);
		}
		else{
			redAttrs.addFlashAttribute("erro", "A quantidade de empréstimos não pode ser maior que 10");
		}

		return "redirect:/emprestimos/";
	}

	@RequestMapping(path="/devolver/{itemId}")
	public String devolver(HttpSession session, @PathVariable("itemId")Long itemId){
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");

		Item item = itemService.getById(itemId);
		Contato contato = item.getContato();
		item.setContato(null);
		item.setDataDevolucao(null);
		itemService.updateItem(item);

		//Enviando e-mail para usuário
		String content = 
				">> O item:\n"
						+ "Item: " + item.getNome() + "\n"
						+ "Descrição: " + item.getDescricao() + "\n\n"
						+ ">> Que havia sido emprestado ao contato:\n"
						+ "Nome: " + contato.getNome() + "\n"
						+ "Endereço: " + contato.getEndereco() + "\n"
						+ "Telefone: " + contato.getTelefone() + "\n\n"
						+ "FOI DEVOLVIDO!";

		EmailBuilder emailBuilder = new EmailBuilder(usuarioLogado.getNome(), 
				usuarioLogado.getEmail(), 
				"Devolução", 
				usuarioLogado.getEmail(), 
				content);

		Email email = new Email(emailBuilder);

		emailService.sendEmail(email);


		return "redirect:/emprestimos/";
	}

}
