package br.com.aweb.sistema_de_vendas.model.dto;

import br.com.aweb.sistema_de_vendas.model.entity.Endereco;
import br.com.aweb.sistema_de_vendas.model.entity.Medico;
import br.com.aweb.sistema_de_vendas.model.enums.Especialidade;

public record DadosDetalhamentoMedico(Long id, String nome, String email, String telefone, String crm,
        Especialidade especialidade, Endereco endereco) {

    public DadosDetalhamentoMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getCrm(),
                medico.getEspecialidade(), medico.getEndereco());
    }
}
