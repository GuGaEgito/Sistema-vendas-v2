package br.com.aweb.sistema_de_vendas.model.dto;

import java.time.LocalDateTime;

import br.com.aweb.sistema_de_vendas.model.entity.Consulta;
import br.com.aweb.sistema_de_vendas.model.enums.StatusConsulta;

public record DadosDetalhamentoConsulta(Long id, Long idMedico, Long idPaciente, LocalDateTime dataHora,
        StatusConsulta status, String motivoCancelamento) {

    public DadosDetalhamentoConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getDataHora(),
                consulta.getStatus(), consulta.getMotivoCancelamento());
    }
}
