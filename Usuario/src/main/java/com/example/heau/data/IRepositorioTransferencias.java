package com.example.heau.data;

import com.example.heau.model.Cliente;
import com.example.heau.model.Conta;
import com.example.heau.model.Transferencia;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IRepositorioTransferencias extends JpaRepository<Transferencia, Long> {

    List<Transferencia> findByContaDestino(Conta conta);
    List<Transferencia> findByContaOrigem(Conta conta);

}
