package com.example.heau.service;

import com.example.heau.data.IRepositorioClientes;
import com.example.heau.data.IRepositorioConta;
import com.example.heau.model.Cliente;
import com.example.heau.model.Conta;
import com.example.heau.model.Transferencia;
import com.example.heau.model.dto.TransferenciaDTO;
import com.example.heau.model.enums.ResultadoTransferenciaEnum;
import com.example.heau.model.enums.TipoTransferenciaEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TransferenciasService {
    private final IRepositorioClientes repositorioCliente;
    private final IRepositorioConta repositorioConta;


    @Autowired
    public TransferenciasService(IRepositorioClientes repositorioCliente, IRepositorioConta repositorioConta) {
        this.repositorioCliente = repositorioCliente;
        this.repositorioConta = repositorioConta;
    }

    public Transferencia realizaTransferencia(TransferenciaDTO transferenciaDTO){

        Conta contaDestino = repositorioCliente.findByConta(
                new Conta(transferenciaDTO.getNumeroContaDestino())).get().getConta();
        Conta contaOrigem = repositorioCliente.findByConta(
                new Conta(transferenciaDTO.getNumeroContaOrigem())).get().getConta();

        if(transferenciaDTO.getValorTransferencia()<=contaOrigem.getSaldo()){

            contaOrigem.setSaldo(contaOrigem.getSaldo()- transferenciaDTO.getValorTransferencia());
            contaOrigem.addTransferencias(new Transferencia(transferenciaDTO, ResultadoTransferenciaEnum.SUCESSO, TipoTransferenciaEnum.ENVIADA));
            repositorioConta.save(contaOrigem);

            contaDestino.setSaldo(contaDestino.getSaldo()+transferenciaDTO.getValorTransferencia());
            contaDestino.addTransferencias(new Transferencia(transferenciaDTO, ResultadoTransferenciaEnum.SUCESSO, TipoTransferenciaEnum.RECEBIDA));
            repositorioConta.save(contaDestino);

        }

        return null;


    }

}
