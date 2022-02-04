package com.br.heau.data;

import com.br.heau.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IRepositorioClientes extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByContaId(Long conta);

}
