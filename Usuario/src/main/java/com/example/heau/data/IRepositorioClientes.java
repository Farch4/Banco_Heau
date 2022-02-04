package com.example.heau.data;

import com.example.heau.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRepositorioClientes extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByContaId(Long conta);

}
