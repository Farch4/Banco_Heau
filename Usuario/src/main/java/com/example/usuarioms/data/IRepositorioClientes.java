package com.example.usuarioms.data;

import com.example.usuarioms.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRepositorioClientes extends JpaRepository<Cliente, Long> {

    /**
     * Consulta personalizada por número da conta bancária
     * @param numero
     * @return Cliente
     */
    Optional<Cliente> findByNumero(Long numero);

}
