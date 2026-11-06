package br.com.aweb.sistema_de_vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.aweb.sistema_de_vendas.model.entity.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

}
