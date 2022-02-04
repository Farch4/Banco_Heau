package com.br.heau.service;

import com.br.heau.data.IRepositorioTransferencias;
import com.br.heau.data.IRepositorioClientes;
import com.br.heau.data.IRepositorioConta;
import com.br.heau.model.Conta;
import com.br.heau.model.Transferencia;
import com.br.heau.model.dto.TransferenciaDTO;
import com.br.heau.model.enums.ResultadoTransferenciaEnum;
import com.br.heau.util.validator.TransferenciaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

        Conta contaDestino = repositorioCliente.findByContaId(transferenciaDTO.getNumeroContaDestino())
                .get().getConta();
        Conta contaOrigem = repositorioCliente.findByContaId(transferenciaDTO.getNumeroContaOrigem())
                .get().getConta();

        contaOrigem.setSaldo(contaOrigem.getSaldo()- transferenciaDTO.getValorTransferencia());
        repositorioConta.save(contaOrigem);
        contaDestino.setSaldo(contaDestino.getSaldo()+transferenciaDTO.getValorTransferencia());
        repositorioConta.save(contaDestino);

        return repositorioTransferencias.save(new Transferencia( contaOrigem, contaDestino,
                transferenciaDTO.getValorTransferencia(), ResultadoTransferenciaEnum.SUCESSO.getResultado()));

    }


    public  List<TransferenciaDTO> listaTransferenciasPorConta(Long numeroConta){
        List<Transferencia> transferencias= new ArrayList<>();
        transferencias.addAll(repositorioTransferencias.findByContaOrigemId(numeroConta));
        transferencias.addAll(repositorioTransferencias.findByContaDestinoId(numeroConta));
        transferencias.sort(Comparator.comparing(Transferencia::getDataDaTransferencia).reversed());
        List<TransferenciaDTO> transferenciasDTO = new ArrayList<>();
        for(Transferencia transferencia: transferencias){
            transferenciasDTO.add(new TransferenciaDTO(transferencia));
        }
        return transferenciasDTO;

    }



}
