package com.br.heau.testes.unitarios.controllers;

import com.br.heau.controller.TransferenciasController;
import com.br.heau.model.dto.TransferenciaDTO;
import com.br.heau.model.enums.ResultadoTransferenciaEnum;
import com.br.heau.service.TransferenciasService;
import com.br.heau.util.excecao.DominioException;
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
public class TransferenciaControllerTests {

    @Mock
    TransferenciasService transferenciasService;

    @InjectMocks
    TransferenciasController transferenciasController;

    @Test
    public void testeDeveriaSucessoAoRealizarTransferencia() throws DominioException {
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
    public void testeDeveriaFalharAoRealizarTransferenciaPorValorNegativo() throws DominioException {
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(geraTransferencia());

        when(transferenciasService.realizaTransferencia(transferenciaDTO))
                .thenThrow(new DominioException(ResultadoTransferenciaEnum.VALOR_INVALIDO.getResultado()));

        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultadoTransferenciaEnum.VALOR_INVALIDO.getResultado()),
                transferenciasController.realizarTransferencia(transferenciaDTO));
    }

    @Test
    public void testeDeveriaFalharAoRealizarTransferenciaPorSaldoInsufuciente() throws DominioException {
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(geraTransferencia());

        when(transferenciasService.realizaTransferencia(transferenciaDTO))
                .thenThrow(new DominioException(ResultadoTransferenciaEnum.SALDO_INCUFICIENTE.getResultado()));

        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultadoTransferenciaEnum.SALDO_INCUFICIENTE.getResultado()),
                transferenciasController.realizarTransferencia(transferenciaDTO));
    }

    @Test
    public void testeDeveriaFalharAoRealizarTransferenciaPorValorMaiorQueMil() throws DominioException {
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(geraTransferencia());

        when(transferenciasService.realizaTransferencia(transferenciaDTO))
                .thenThrow(new DominioException(ResultadoTransferenciaEnum.VALOR_MAIOR.getResultado()));

        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultadoTransferenciaEnum.VALOR_MAIOR.getResultado()),
                transferenciasController.realizarTransferencia(transferenciaDTO));
    }
    @Test
    public void testeDeveriaFalharAoRealizarTransferenciaPorContaOrigemInexistente() throws DominioException {
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(geraTransferencia());

        when(transferenciasService.realizaTransferencia(transferenciaDTO))
                .thenThrow(new DominioException(ResultadoTransferenciaEnum.CONTA_ORIGEM_INEXISTENTE.getResultado()));

        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultadoTransferenciaEnum.CONTA_ORIGEM_INEXISTENTE.getResultado()),
                transferenciasController.realizarTransferencia(transferenciaDTO));
    }
    @Test
    public void testeDeveriaFalharAoRealizarTransferenciaPorContaDestinoInexistente() throws DominioException {
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(geraTransferencia());

        when(transferenciasService.realizaTransferencia(transferenciaDTO))
                .thenThrow(new DominioException(ResultadoTransferenciaEnum.CONTA_DESTINO_INEXISTENTE.getResultado()));

        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultadoTransferenciaEnum.CONTA_DESTINO_INEXISTENTE.getResultado()),
                transferenciasController.realizarTransferencia(transferenciaDTO));
    }

    @Test
    public void deveriaTerSucessoAoListarTransferenciasPorConta(){

        List transferenciasDTO = new ArrayList<>();
        transferenciasDTO.add(new TransferenciaDTO(geraTransferencia()));
        transferenciasDTO.add(new TransferenciaDTO(geraTransferencia()));

        when(transferenciasService.listaTransferenciasPorConta(2L))
                .thenReturn(transferenciasDTO);

        assertEquals(ResponseEntity.ok(transferenciasDTO), transferenciasController.listaTransferencias(2L));

    }

    @Test
    public void deveriaRetornarListaVaziaAoListarTransferenciasPorConta(){

        when(transferenciasService.listaTransferenciasPorConta(2L))
                .thenReturn(new ArrayList<>());

        assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT).body(null),
                transferenciasController.listaTransferencias(2L));

    }


}