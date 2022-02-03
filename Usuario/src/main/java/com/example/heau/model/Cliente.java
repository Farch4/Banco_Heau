package com.example.heau.model;

import com.example.heau.model.dto.ClienteDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "CLIENTE")
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cliente {

    public Cliente(ClienteDTO clienteDTO){
        this.nome= clienteDTO.getNome();
        this.conta= new Conta(clienteDTO.getSaldo());
    }

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @JsonIgnore
    private Long id;

    @Column(nullable = false)
    @JsonProperty("Nome do Cliente")
    private String nome;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Conta conta;
}
