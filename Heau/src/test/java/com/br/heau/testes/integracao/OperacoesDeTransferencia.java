package com.br.heau.testes.integracao;

import com.br.heau.controller.ClienteController;
import com.br.heau.controller.TransferenciasController;
import com.br.heau.model.dto.TransferenciaDTO;
import com.br.heau.model.enums.ResultadoTransferenciaEnum;
import com.br.heau.repository.IRepositorioClientes;
import com.br.heau.service.ClienteService;
import com.br.heau.service.TransferenciasService;
import com.br.heau.testes.ITestCasesTransferencias;
import com.br.heau.util.exception.DominioException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.br.heau.testes.utils.GeradoraDeEntidades.geraClienteDTO;
import static com.br.heau.testes.utils.GeradoraDeEntidades.geraTransferencia;
import static com.br.heau.util.GeradoraDeMensagens.mensagemSucessoTransferencia;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class OperacoesDeTransferencia implements ITestCasesTransferencias {

    @Autowired
    TransferenciasController transferenciasController;


    @Test
    public void deveriaSucessoAoRealizarTransferencia() {

    }

    @Test
    public void deveriaDarErroDeValorMaiorQueMilAoRealizarTransferencia() throws DominioException {
        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultadoTransferenciaEnum.VALOR_MAIOR.getResultado()),
                transferenciasController.realizarTransferencia(new TransferenciaDTO(geraTransferencia(1000.1))));
    }

    @Test
    public void deveriaDarErroDeContaOrigemInexistenteAoRealizarTransferencia() {

    }

    @Test
    public void deveriaDarErroDeContaDestinoInexistenteAoRealizarTransferencia(){

    }

    @Test
    public void deveriaDarErroDeContasInexistentesAoRealizarTransferencia(){
        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResultadoTransferenciaEnum.CONTAS_INEXISTENTES.getResultado()),
                transferenciasController.realizarTransferencia(new TransferenciaDTO(geraTransferencia(1000.1))));

    }

    @Test
    public void deveriaDarErroComValorMenorQueZero(){

    }

    @Test
    public void deveriaDarErroDeSaldoInsuficiente(){

    }

    @Test
    public void deveriaSucessoAoListarTransferenciasPorConta() {

    }

    @Test
    public void deveriaTrazerResultadoVazioAoAcessarTransferenciasPorConta() {

    }
}
