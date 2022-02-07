package com.br.heau.service;

import com.br.heau.repository.IRepositorioTransferencias;
import com.br.heau.repository.IRepositorioClientes;
import com.br.heau.repository.IRepositorioConta;
import com.br.heau.model.Cliente;
import com.br.heau.model.Conta;
import com.br.heau.model.Transferencia;
import com.br.heau.model.dto.TransferenciaDTO;
import com.br.heau.model.enums.ResultadoTransferenciaEnum;
import com.br.heau.util.exception.DominioException;
import com.br.heau.util.validator.TransferenciaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.br.heau.util.GeradoraDeMensagens.mensagemSucessoTransferencia;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

    public String realizaTransferencia(TransferenciaDTO transferenciaDTO) throws DominioException {
        TransferenciaValidator validador = new TransferenciaValidator(repositorioCliente, transferenciaDTO);

        Optional<Cliente> clienteDestino = repositorioCliente.findByContaId(transferenciaDTO.getNumeroContaDestino());
        Optional<Cliente> clienteOrigem = repositorioCliente.findByContaId(transferenciaDTO.getNumeroContaOrigem());
        Conta contaDestino= clienteDestino.map(Cliente::getConta).orElse(null);
        Conta contaOrigem= clienteOrigem.map(Cliente::getConta).orElse(null);

        try {
            validador.validate();

            contaOrigem.setSaldo(contaOrigem.getSaldo() - transferenciaDTO.getValorTransferencia());
            repositorioConta.save(contaOrigem);
            contaDestino.setSaldo(contaDestino.getSaldo() + transferenciaDTO.getValorTransferencia());
            repositorioConta.save(contaDestino);

            repositorioTransferencias.save(new Transferencia(contaOrigem, contaDestino,
                    transferenciaDTO.getValorTransferencia(), ResultadoTransferenciaEnum.SUCESSO.getResultado()));

            return mensagemSucessoTransferencia(clienteOrigem.get().getNome(), clienteDestino.get().getNome(),
                    transferenciaDTO.getValorTransferencia().toString());

        }catch (DominioException e){
            repositorioTransferencias.save(new Transferencia(contaOrigem,
                    contaDestino, transferenciaDTO.getValorTransferencia(),
                    e.getMensagensDeErro()));
            throw e;
        }
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
