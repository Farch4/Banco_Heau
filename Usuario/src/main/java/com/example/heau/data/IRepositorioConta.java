package com.example.heau.data;

import com.example.heau.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositorioConta extends JpaRepository<Conta, Long> {

}
