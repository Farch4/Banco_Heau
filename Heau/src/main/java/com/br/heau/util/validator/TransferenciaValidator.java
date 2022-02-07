package com.br.heau.util.validator;

import com.br.heau.model.Cliente;
import com.br.heau.model.Conta;
import com.br.heau.model.dto.TransferenciaDTO;
import com.br.heau.repository.IRepositorioClientes;
import com.br.heau.util.Constantes;
import com.br.heau.util.exception.DominioException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class TransferenciaValidator {

    private final IRepositorioClientes repositorioCliente;
    private final TransferenciaDTO transferenciaDTO;
    private String mensagemDeErro;

    public TransferenciaValidator(IRepositorioClientes repositorioCliente, TransferenciaDTO transferenciaDTO) {
        this.repositorioCliente = repositorioCliente;
        this.transferenciaDTO = transferenciaDTO;
        this.mensagemDeErro = "";
    }

    public String validate() throws DominioException {
        return validaContas();
    }

    private String validaContas() {
        Optional<Cliente> clienteDestino = repositorioCliente.findByContaId(
                transferenciaDTO.getNumeroContaDestino());
        Optional<Cliente> clienteOrigem = repositorioCliente.findByContaId(
                transferenciaDTO.getNumeroContaOrigem());

        if (!(clienteDestino.isPresent()) && !(clienteOrigem.isPresent())) {
            setMensagemDeErro(mensagemDeErro.concat(Constantes.CONTAS_INEXISTENTES));
            return clienteOrigem.isPresent() ? validaValorSaldo(clienteOrigem.get().getConta()) : validaValorTransferencia();
        }

        if (!(clienteOrigem.isPresent()) && (clienteDestino.isPresent())) {
            setMensagemDeErro(mensagemDeErro.concat(Constantes.CONTA_ORIGEM_INEXISTENTE));

        }
        if (!(clienteDestino.isPresent()) && (clienteOrigem.isPresent())) {
            setMensagemDeErro(mensagemDeErro.concat(Constantes.CONTA_DESTINO_INEXISTENTE));
        }

        return clienteOrigem.isPresent() ? validaValorSaldo(clienteOrigem.get().getConta()) : validaValorTransferencia();

    }

    private String validaValorSaldo(Conta contaOrigem) {
        if (transferenciaDTO.getValorTransferencia() > contaOrigem.getSaldo()) {
            setMensagemDeErro(mensagemDeErro.concat(Constantes.SALDO_INCUFICIENTE));
        }
        return validaValorTransferencia();
    }


    private String validaValorTransferencia() {
        if (transferenciaDTO.getValorTransferencia() > 1000) {
            setMensagemDeErro(mensagemDeErro.concat(Constantes.VALOR_MAIOR));
        }
        if (transferenciaDTO.getValorTransferencia() < 0) {
            setMensagemDeErro(mensagemDeErro.concat(Constantes.VALOR_INVALIDO));
        }
        return mensagemDeErro;
    }


}
