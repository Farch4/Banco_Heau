package com.example.usuarioms.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "clientes")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cliente {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    @JsonProperty("Nome do Cliente")
    private String nome;

    @EqualsAndHashCode.Include
    @JsonProperty("Número da Conta")
    @Column(unique = true, nullable = false)
    private Long numero;

    @JsonProperty("Saldo da Conta")
    @Column
    private Double saldo;

}
