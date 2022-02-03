package com.example.heau.data;

import com.example.heau.model.Cliente;
import com.example.heau.model.Conta;
import com.example.heau.model.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRepositorioTransferencias extends JpaRepository<Transferencia, Long> {

    Optional<Transferencia> findByNumeroContaDestino(Long numeroContaDestino);
    Optional<Transferencia> findByNumeroContaOrigem(Long numeroContaOrigem);
}
