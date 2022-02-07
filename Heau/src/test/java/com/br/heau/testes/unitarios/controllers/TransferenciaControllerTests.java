package com.br.heau.testes.unitarios.controllers;

import com.br.heau.controller.TransferenciasController;
import com.br.heau.model.dto.TransferenciaDTO;
import com.br.heau.service.TransferenciasService;
import com.br.heau.testes.ITestCasesTransferencias;
import com.br.heau.util.Constantes;
import com.br.heau.util.exception.DominioException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static com.br.heau.testes.utils.GeradoraDeEntidades.geraTransferencia;
import static com.br.heau.util.GeradoraDeMensagens.mensagemSucessoTransferencia;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TransferenciaControllerTests implements ITestCasesTransferencias {

    @Mock
    TransferenciasService transferenciasService;

    @InjectMocks
    TransferenciasController transferenciasController;

    @Test
    public void deveriaSucessoAoRealizarTransferencia() throws DominioException {
        String contaOrigem = "2";
        String contaDestino = "3";
        String valor = "20";
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(geraTransferencia());

        when(transferenciasService.realizaTransferencia(transferenciaDTO))
                .thenReturn(mensagemSucessoTransferencia(contaOrigem, contaDestino, valor));

        assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(mensagemSucessoTransferencia(contaOrigem, contaDestino, valor)),
                transferenciasController.realizarTransferencia(transferenciaDTO));
    }

    @Test
    public void deveriaDarErroComValorMenorQueZero() throws DominioException {
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(geraTransferencia());

        when(transferenciasService.realizaTransferencia(transferenciaDTO))
                .thenThrow(new DominioException(Constantes.VALOR_INVALIDO));

        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constantes.VALOR_INVALIDO),
                transferenciasController.realizarTransferencia(transferenciaDTO));
    }

    @Test
    public void deveriaDarErroDeSaldoInsuficiente() throws DominioException {
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(geraTransferencia());

        when(transferenciasService.realizaTransferencia(transferenciaDTO))
                .thenThrow(new DominioException(Constantes.SALDO_INCUFICIENTE));

        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constantes.SALDO_INCUFICIENTE),
                transferenciasController.realizarTransferencia(transferenciaDTO));
    }

    @Test
    public void deveriaDarErroDeValorMaiorQueMilAoRealizarTransferencia() throws DominioException {
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(geraTransferencia());

        when(transferenciasService.realizaTransferencia(transferenciaDTO))
                .thenThrow(new DominioException(Constantes.VALOR_MAIOR));

        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constantes.VALOR_MAIOR),
                transferenciasController.realizarTransferencia(transferenciaDTO));
    }
    @Test
    public void deveriaDarErroDeContaOrigemInexistenteAoRealizarTransferencia() throws DominioException {
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(geraTransferencia());

        when(transferenciasService.realizaTransferencia(transferenciaDTO))
                .thenThrow(new DominioException(Constantes.CONTA_ORIGEM_INEXISTENTE));

        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constantes.CONTA_ORIGEM_INEXISTENTE),
                transferenciasController.realizarTransferencia(transferenciaDTO));
    }

    @Test
    public void deveriaDarErroDeContasInexistentesAoRealizarTransferencia() throws DominioException{
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(geraTransferencia());

        when(transferenciasService.realizaTransferencia(transferenciaDTO))
                .thenThrow(new DominioException(Constantes.CONTAS_INEXISTENTES));

        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constantes.CONTAS_INEXISTENTES),
                transferenciasController.realizarTransferencia(transferenciaDTO));
    }


    @Test
    public void deveriaDarErroDeContaDestinoInexistenteAoRealizarTransferencia() throws DominioException {
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(geraTransferencia());

        when(transferenciasService.realizaTransferencia(transferenciaDTO))
                .thenThrow(new DominioException(Constantes.CONTA_DESTINO_INEXISTENTE));

        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constantes.CONTA_DESTINO_INEXISTENTE),
                transferenciasController.realizarTransferencia(transferenciaDTO));
    }


    @Test
    public void deveriaSucessoAoListarTransferenciasPorConta(){

        List transferenciasDTO = new ArrayList<>();
        transferenciasDTO.add(new TransferenciaDTO(geraTransferencia()));
        transferenciasDTO.add(new TransferenciaDTO(geraTransferencia()));

        when(transferenciasService.listaTransferenciasPorConta(2L))
                .thenReturn(transferenciasDTO);

        assertEquals(ResponseEntity.ok(transferenciasDTO), transferenciasController.listaTransferencias(2L));

    }

    @Test
    public void deveriaTrazerResultadoVazioAoAcessarTransferenciasPorConta(){

        when(transferenciasService.listaTransferenciasPorConta(2L))
                .thenReturn(new ArrayList<>());

        assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT).body(null),
                transferenciasController.listaTransferencias(2L));

    }


}