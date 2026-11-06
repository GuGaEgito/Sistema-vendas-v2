package br.com.aweb.sistema_de_vendas.model.dto;

import jakarta.validation.Valid;

public record DadosAtualizacaoPaciente(String nome, String telefone, @Valid DadosEndereco endereco) {
}
