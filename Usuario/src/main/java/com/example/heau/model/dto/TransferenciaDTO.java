package com.example.heau.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaDTO {

    @JsonProperty("Número da Conta Origem")
    private Long numeroContaOrigem;

    @JsonProperty("Número da Conta Destino")
    private Long numeroContaDestino;

    @JsonProperty("Valor da Transferência")
    private Double valorTransferencia;
}
