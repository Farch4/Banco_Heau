package com.br.heau.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Data
@Entity
@Table(name = "CONTA")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("Número da Conta")
    @Column(unique = true, nullable = false)
    private Long id;

    @Version
    @JsonIgnore
    private Integer versao;

    @Column(nullable = false)
    @JsonProperty("Saldo da Conta")
    private Double saldo;

    public Conta(Double saldo) {
        this.saldo= saldo;
    }

    public Conta(Long id) {
        this.id = id;
    }
}
