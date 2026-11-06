package br.com.aweb.sistema_de_vendas.model.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record DadosAgendamentoConsulta(

        @NotNull Long idMedico,

        @NotNull Long idPaciente,

        @NotNull @Future LocalDateTime dataHora) {
}
