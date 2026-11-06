package br.com.aweb.sistema_de_vendas.model.dto;

import br.com.aweb.sistema_de_vendas.model.entity.Medico;
import br.com.aweb.sistema_de_vendas.model.enums.Especialidade;

public record DadosListagemMedico(Long id, String nome, String email, String crm, Especialidade especialidade) {

    public DadosListagemMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
}
