package com.example.heau.data;
import com.example.heau.model.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRepositorioTransferencias extends JpaRepository<Transferencia, Long> {

    List<Transferencia> findByContaDestinoId(Long conta);
    List<Transferencia> findByContaOrigemId(Long conta);

}
