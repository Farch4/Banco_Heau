package com.example.heau.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    @JsonProperty("Nome do Cliente")
    private String nome;

    @JsonProperty("Saldo Inicial")
    private Double saldo;
}
