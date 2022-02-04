package com.br.heau.data;

import com.br.heau.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositorioConta extends JpaRepository<Conta, Long> {

}
