package br.com.aweb.sistema_de_vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.aweb.sistema_de_vendas.model.entity.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

}
