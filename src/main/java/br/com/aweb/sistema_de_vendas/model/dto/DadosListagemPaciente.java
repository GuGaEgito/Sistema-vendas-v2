package br.com.aweb.sistema_de_vendas.model.dto;

import br.com.aweb.sistema_de_vendas.model.entity.Paciente;

public record DadosListagemPaciente(Long id, String nome, String email, String cpf) {

    public DadosListagemPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }
}
