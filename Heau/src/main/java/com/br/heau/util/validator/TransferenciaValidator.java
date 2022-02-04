package com.br.heau.util.validator;

import com.br.heau.data.IRepositorioTransferencias;
import com.br.heau.model.Cliente;
import com.br.heau.model.Conta;
import com.br.heau.model.dto.TransferenciaDTO;
import com.br.heau.model.enums.ResultadoTransferenciaEnum;
import com.br.heau.data.IRepositorioClientes;
import com.br.heau.model.Transferencia;

import java.util.Optional;

public class TransferenciaValidator {

    private final IRepositorioTransferencias repositorioTransferencias;
    private final IRepositorioClientes repositorioCliente;
    private final TransferenciaDTO transferenciaDTO;

    public TransferenciaValidator(IRepositorioTransferencias repositorioTransferencias, IRepositorioClientes repositorioCliente, TransferenciaDTO transferenciaDTO) {
        this.repositorioTransferencias = repositorioTransferencias;
        this.repositorioCliente = repositorioCliente;
        this.transferenciaDTO = transferenciaDTO;
    }

    public Transferencia validate(){
        return validaContas();
    }

    private Transferencia validaContas(){
        Optional<Cliente> clienteDestino = repositorioCliente.findByContaId(
                transferenciaDTO.getNumeroContaDestino());
        Optional<Cliente> clienteOrigem = repositorioCliente.findByContaId(
                transferenciaDTO.getNumeroContaOrigem());

        if(!(clienteOrigem.isPresent()) && (clienteDestino.isPresent())){
            return repositorioTransferencias.save(new Transferencia(null,
                    clienteDestino.get().getConta(), transferenciaDTO.getValorTransferencia(),
                    ResultadoTransferenciaEnum.CONTA_ORIGEM_INEXISTENTE.getResultado()));
        }
        if(!(clienteDestino.isPresent())&&(clienteOrigem.isPresent())){
            return repositorioTransferencias.save(new Transferencia(clienteOrigem.get().getConta(),
                    null, transferenciaDTO.getValorTransferencia(),
                    ResultadoTransferenciaEnum.CONTA_DESTINO_INEXISTENTE.getResultado()));
        }
        if(!(clienteDestino.isPresent()) && !(clienteOrigem.isPresent())){
            return repositorioTransferencias.save(new Transferencia(null,
                    null, transferenciaDTO.getValorTransferencia(),
                    ResultadoTransferenciaEnum.CONTAS_INEXISTENTES.getResultado()));
        }

        return validaValor(clienteOrigem.get().getConta(), clienteDestino.get().getConta());

    }

    private Transferencia validaValor(Conta contaOrigem, Conta contaDestino) {
        if (transferenciaDTO.getValorTransferencia() > 1000) {
            return repositorioTransferencias.save(new Transferencia(contaOrigem,
                    contaDestino, transferenciaDTO.getValorTransferencia(),
                    ResultadoTransferenciaEnum.VALOR_MAIOR.getResultado()));
        }
        if (transferenciaDTO.getValorTransferencia() > contaOrigem.getSaldo()) {
            return repositorioTransferencias.save(new Transferencia(contaOrigem,
                    contaDestino, transferenciaDTO.getValorTransferencia(),
                    ResultadoTransferenciaEnum.SALDO_INCUFICIENTE.getResultado()));
        }

        return null;
    }


}
