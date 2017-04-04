package br.ufc.npi.emprestaai.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ufc.npi.emprestaai.bean.Item;

@Repository
@Transactional
public interface ItemRepository extends JpaRepository<Item, Long>{

	Item findByNome(String nome);
	List<Item> findByUsuarioId(Long usuarioId);
	@Query(value="SELECT * FROM item AS i WHERE i.contato_id IS NULL AND i.usuario_id = ?1", nativeQuery=true)
	List<Item> findDisponiveis(Long usuarioId);
	
	@Query(value="SELECT count(*) FROM item AS i WHERE i.contato_id IS NOT NULL", nativeQuery=true)
	Integer countItensEmprestados();
}
