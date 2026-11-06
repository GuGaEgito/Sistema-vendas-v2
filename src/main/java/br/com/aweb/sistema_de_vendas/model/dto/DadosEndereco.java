package br.com.aweb.sistema_de_vendas.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(

        @NotBlank String logradouro,

        @NotBlank String bairro,

        @NotBlank @Pattern(regexp = "\\d{8}") String cep,

        @NotBlank String cidade,

        @NotBlank @Pattern(regexp = "[A-Za-z]{2}") String uf,

        String numero,
        String complemento) {
}
