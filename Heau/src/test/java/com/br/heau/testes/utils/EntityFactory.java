package com.br.heau.testes.utils;

import com.br.heau.model.Cliente;
import com.br.heau.model.Conta;
import com.br.heau.model.Transferencia;
import com.br.heau.model.dto.ClienteDTO;
import com.br.heau.model.enums.ResultadoTransferenciaEnum;

import java.util.Date;

public class EntityFactory {

    public static Cliente geraCliente(Long codigo){
        return new Cliente((new ClienteDTO("Bruno", 1000.0, codigo)));
    }

    public static Transferencia geraTransferencia(Conta contaOrigem, Conta contaDestino, String resultado){
        return new Transferencia(contaOrigem, contaDestino, 10.0, resultado);
    }

    public static Transferencia geraTransferencia(Conta contaOrigem, Conta contaDestino, String resultado, Double valor){
        return new Transferencia(contaOrigem, contaDestino, valor, resultado);
    }

    public static Transferencia geraTransferencia(Long id, Conta contaOrigem, Conta contaDestino, String resultado){
        return new Transferencia(id, 0, contaOrigem, contaDestino, 10.0, resultado, new Date(System.currentTimeMillis()));
    }

    public static Conta geraConta(Long id){
        return new Conta(id);
    }

    public static ClienteDTO geraClienteDTO(){
        return new ClienteDTO("Bruno", 1000.0, 9L);
    }
}
