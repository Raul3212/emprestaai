package br.ufc.npi.emprestaai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.emprestaai.bean.Item;
import br.ufc.npi.emprestaai.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	ItemRepository itemRepository;
	
	public Item getById(Long id){
		return itemRepository.findOne(id);
	}
	
	public void saveItem(Item item){
		itemRepository.save(item);
	}
	
	public List<Item> listByUsuarioId(Long usuarioId){
		return itemRepository.findByUsuarioId(usuarioId);
	}
	
	public void deleteItem(Long itemId){
		Item item = new Item();
		item.setId(itemId);
		itemRepository.delete(item);
	}
	
	public List<Item> findDisponibleItens(Long usuarioId){
		return itemRepository.findDisponiveis(usuarioId);
	}
	
	public void updateItem(Item item){
		itemRepository.save(item);
	}
	
	public Integer countItensEmprestados(){
		return itemRepository.countItensEmprestados();
	}
	
}
