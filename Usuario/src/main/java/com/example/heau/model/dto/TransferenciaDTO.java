package com.example.heau.model.dto;

import com.example.heau.model.Transferencia;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaDTO {

    public TransferenciaDTO(Transferencia transferencia){
        this.numeroContaOrigem= transferencia.getContaOrigem().getId();
        this.numeroContaDestino= transferencia.getContaDestino().getId();
        this.valorTransferencia= transferencia.getValorTransferencia();
        this.dataTransferencia= transferencia.getDataDaTransferencia();
        this.resultado= transferencia.getResultado();
    }

    @JsonProperty("Número da Conta Origem:")
    private Long numeroContaOrigem;

    @JsonProperty("Número da Conta Destino:")
    private Long numeroContaDestino;

    @JsonProperty("Valor da Transferência:")
    private Double valorTransferencia;

    @JsonProperty("Data da Transferência:")
    private Date dataTransferencia;

    @JsonProperty("Resultado da Operação:")
    private String resultado;


}
