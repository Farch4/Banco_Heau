package com.example.heau.service;

import com.example.heau.data.IRepositorioClientes;
import com.example.heau.data.IRepositorioConta;
import com.example.heau.data.IRepositorioTransferencias;
import com.example.heau.model.Cliente;
import com.example.heau.model.Conta;
import com.example.heau.model.Transferencia;
import com.example.heau.model.dto.TransferenciaDTO;
import com.example.heau.model.enums.ResultadoTransferenciaEnum;
import com.example.heau.util.validator.TransferenciaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransferenciasService {
    private final IRepositorioClientes repositorioCliente;
    private final IRepositorioConta repositorioConta;
    private final IRepositorioTransferencias repositorioTransferencias;



    @Autowired
    public TransferenciasService(IRepositorioClientes repositorioCliente, IRepositorioConta repositorioConta, IRepositorioTransferencias repositorioTransferencias) {
        this.repositorioCliente = repositorioCliente;
        this.repositorioConta = repositorioConta;
        this.repositorioTransferencias = repositorioTransferencias;
    }


    public Transferencia realizaTransferencia(TransferenciaDTO transferenciaDTO){

        TransferenciaValidator validador = new TransferenciaValidator(repositorioTransferencias, repositorioCliente, transferenciaDTO);

        if(validador.validate()!=null){
            return validador.validate();
        }

        Conta contaDestino = repositorioCliente.findByConta(
                new Conta(transferenciaDTO.getNumeroContaDestino())).get().getConta();
        Conta contaOrigem = repositorioCliente.findByConta(
                new Conta(transferenciaDTO.getNumeroContaOrigem())).get().getConta();

        contaOrigem.setSaldo(contaOrigem.getSaldo()- transferenciaDTO.getValorTransferencia());
        repositorioConta.save(contaOrigem);
        contaDestino.setSaldo(contaDestino.getSaldo()+transferenciaDTO.getValorTransferencia());
        repositorioConta.save(contaDestino);

        return repositorioTransferencias.save(new Transferencia( contaOrigem, contaDestino,
                transferenciaDTO.getValorTransferencia(), ResultadoTransferenciaEnum.SUCESSO.getResultado()));

    }


    public  List<TransferenciaDTO> listaTransferenciasPorConta(Long numeroConta){
        List<Transferencia> transferencias= new ArrayList<>();
        transferencias.addAll(repositorioTransferencias.findByContaOrigem(repositorioConta.getById(numeroConta)));
        transferencias.addAll(repositorioTransferencias.findByContaDestino(repositorioConta.getById(numeroConta)));
        Comparator<Transferencia> comparator= Comparator.comparing(Transferencia::getDataDaTransferencia);
        transferencias.sort(comparator.reversed());
        List<TransferenciaDTO> transferenciasDTO = new ArrayList<>();
        for(Transferencia transferencia: transferencias){
            transferenciasDTO.add(new TransferenciaDTO(transferencia));
        }
        return transferenciasDTO;

    }



}
