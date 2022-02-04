package com.br.heau.model.dto;

import com.br.heau.model.Transferencia;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaDTO {

    /**
     * Construtor que converte de antidade pra DTO
     * @param transferencia
     */
    public TransferenciaDTO(Transferencia transferencia){
        this.id=transferencia.getId();
        this.numeroContaOrigem= transferencia.getContaOrigem()!=null?transferencia.getContaOrigem().getId():null;
        this.numeroContaDestino= transferencia.getContaDestino()!=null?transferencia.getContaDestino().getId():null;
        this.valorTransferencia= transferencia.getValorTransferencia();
        this.dataTransferencia= transferencia.getDataDaTransferencia();
        this.resultado= transferencia.getResultado();
    }

    @JsonIgnore
    private Long id;

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
