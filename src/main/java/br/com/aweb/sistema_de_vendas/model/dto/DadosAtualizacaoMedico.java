package br.com.aweb.sistema_de_vendas.model.dto;

import jakarta.validation.Valid;

public record DadosAtualizacaoMedico(String nome, String telefone, @Valid DadosEndereco endereco) {
}
