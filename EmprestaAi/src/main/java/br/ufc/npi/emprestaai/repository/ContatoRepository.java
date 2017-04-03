package br.ufc.npi.emprestaai.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufc.npi.emprestaai.bean.Contato;

@Repository
@Transactional
public interface ContatoRepository extends JpaRepository<Contato, Long>{
	
	Contato findByNome(String nome);
	List<Contato> findByUsuarioId(Long usuarioId);
	
}
