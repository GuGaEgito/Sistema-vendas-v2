package br.com.aweb.sistema_de_vendas.model.dto;

import br.com.aweb.sistema_de_vendas.model.entity.Endereco;
import br.com.aweb.sistema_de_vendas.model.entity.Paciente;

public record DadosDetalhamentoPaciente(Long id, String nome, String email, String telefone, String cpf,
        Endereco endereco) {

    public DadosDetalhamentoPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf(),
                paciente.getEndereco());
    }
}
