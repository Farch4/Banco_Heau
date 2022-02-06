package com.br.heau.util.validator;

import com.br.heau.data.IRepositorioTransferencias;
import com.br.heau.model.Cliente;
import com.br.heau.model.Conta;
import com.br.heau.model.dto.TransferenciaDTO;
import com.br.heau.model.enums.ResultadoTransferenciaEnum;
import com.br.heau.data.IRepositorioClientes;
import com.br.heau.model.Transferencia;
import com.br.heau.util.excecao.DominioException;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class TransferenciaValidator {

    private final IRepositorioClientes repositorioCliente;
    private final TransferenciaDTO transferenciaDTO;


    public Boolean validate() throws DominioException {
        return validaContas();
    }

    private Boolean validaContas() throws DominioException {
        Optional<Cliente> clienteDestino = repositorioCliente.findByContaId(
                transferenciaDTO.getNumeroContaDestino());
        Optional<Cliente> clienteOrigem = repositorioCliente.findByContaId(
                transferenciaDTO.getNumeroContaOrigem());

        if(!(clienteOrigem.isPresent()) && (clienteDestino.isPresent())){
            throw new DominioException(ResultadoTransferenciaEnum.CONTA_ORIGEM_INEXISTENTE.getResultado());
        }
        if(!(clienteDestino.isPresent())&&(clienteOrigem.isPresent())){
            throw new DominioException(ResultadoTransferenciaEnum.CONTA_DESTINO_INEXISTENTE.getResultado());
        }
        if(!(clienteDestino.isPresent()) && !(clienteOrigem.isPresent())){
            throw new DominioException(ResultadoTransferenciaEnum.CONTAS_INEXISTENTES.getResultado());
        }

        return validaValor(clienteOrigem.get().getConta());

    }

    private Boolean validaValor(Conta contaOrigem) throws DominioException {
        if (transferenciaDTO.getValorTransferencia() > 1000) {
            throw new DominioException(ResultadoTransferenciaEnum.VALOR_MAIOR.getResultado());
        }
        if (transferenciaDTO.getValorTransferencia() > contaOrigem.getSaldo()) {
            throw new DominioException(ResultadoTransferenciaEnum.SALDO_INCUFICIENTE.getResultado());
        }
        if (transferenciaDTO.getValorTransferencia() < 0) {
            throw new DominioException(ResultadoTransferenciaEnum.VALOR_INVALIDO.getResultado());
        }

        return true;
    }


}
