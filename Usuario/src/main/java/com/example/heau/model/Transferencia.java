package com.example.heau.model;

import com.example.heau.model.dto.TransferenciaDTO;
import com.example.heau.model.enums.ResultadoTransferenciaEnum;
import com.example.heau.model.enums.TipoTransferenciaEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "TRANSFERENCIA")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Transferencia {

    public Transferencia(TransferenciaDTO transferenciaDTO, ResultadoTransferenciaEnum resultado, TipoTransferenciaEnum tipo){
        this.numeroContaOrigem= transferenciaDTO.getNumeroContaOrigem();
        this.numeroContaDestino= transferenciaDTO.getNumeroContaDestino();
        this.valorTransferencia= transferenciaDTO.getValorTransferencia();
        this.resultado= resultado;
        this.tipo = tipo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonIgnore
    private Long id;

    @Column(nullable = false)
    @JsonProperty("Número da Conta Origem")
    private Long numeroContaOrigem;

    @Column(nullable = false)
    @JsonProperty("Número da Conta Destino")
    private Long numeroContaDestino;

    @Column(nullable = false)
    @JsonProperty("Valor da Transferência")
    private Double valorTransferencia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @JsonProperty("Resultado da Transferência:")
    private ResultadoTransferenciaEnum resultado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @JsonProperty("Tipo de operação:")
    private TipoTransferenciaEnum tipo;

    public Transferencia setTipo(TipoTransferenciaEnum tipo) {
        this.tipo = tipo;
        return this;
    }

}
