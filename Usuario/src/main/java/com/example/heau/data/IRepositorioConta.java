package com.example.heau.data;

import com.example.heau.model.Cliente;
import com.example.heau.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRepositorioConta extends JpaRepository<Conta, Long> {

}
