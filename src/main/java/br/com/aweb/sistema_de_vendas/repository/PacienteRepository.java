package br.com.aweb.sistema_de_vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.aweb.sistema_de_vendas.model.entity.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

}
