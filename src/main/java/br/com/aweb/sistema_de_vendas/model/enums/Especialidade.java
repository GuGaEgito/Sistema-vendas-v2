package br.com.aweb.sistema_de_vendas.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Especialidade {
    CARDIOLOGIA,
    DERMATOLOGIA,
    GINECOLOGIA,
    ORTOPEDIA;

    // Aceita "ortopedia" no JSON, como no exemplo do Insomnia
    @JsonCreator
    public static Especialidade fromJson(String valor) {
        return Especialidade.valueOf(valor.trim().toUpperCase());
    }
}
