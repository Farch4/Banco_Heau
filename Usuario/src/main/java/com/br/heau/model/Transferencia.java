package com.br.heau.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.Date;


@Data
@Entity
@Table(name = "TRANSFERENCIA")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @JsonIgnore
    private Long id;

    @Version
    @JsonIgnore
    private Integer versao;

    @JsonProperty("Número da Conta Origem")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "conta_origem", referencedColumnName = "id")
    private Conta contaOrigem;

    @JsonProperty("Número da Conta Destino")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "conta_destino", referencedColumnName = "id")
    private Conta contaDestino;

    @Column(nullable = false)
    @JsonProperty("Valor da Transferência")
    private Double valorTransferencia;

    @Column(nullable = false)
    @JsonProperty("Resultado da Transferência:")
    private String resultado;

    @Column(nullable = false)
    @JsonIgnore
    @JsonProperty("Data da Transferência")
    private Date dataDaTransferencia;

    public Transferencia(Conta contaOrigem, Conta contaDestino, Double valorTransferencia, String resultado) {
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valorTransferencia = valorTransferencia;
        this.resultado = resultado;
        dataDaTransferencia = new Date(System.currentTimeMillis());
    }
}
