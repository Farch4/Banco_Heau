package com.br.heau.testes.integracao;

import com.br.heau.controller.TransferenciasController;
import com.br.heau.model.Cliente;
import com.br.heau.model.Transferencia;
import com.br.heau.model.dto.TransferenciaDTO;
import com.br.heau.model.enums.ResultadoTransferenciaEnum;
import com.br.heau.repository.IRepositorioClientes;
import com.br.heau.repository.IRepositorioConta;
import com.br.heau.repository.IRepositorioTransferencias;
import com.br.heau.service.TransferenciasService;
import com.br.heau.testes.ITestCasesTransferencias;
import com.br.heau.util.Constantes;
import com.br.heau.util.exception.DominioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.br.heau.testes.utils.GeradoraDeEntidades.geraCliente;
import static com.br.heau.testes.utils.GeradoraDeEntidades.geraConta;
import static com.br.heau.testes.utils.GeradoraDeEntidades.geraTransferencia;
import static com.br.heau.util.GeradoraDeMensagens.mensagemSucessoTransferencia;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class OperacoesDeTransferencia implements ITestCasesTransferencias {

    @Autowired
    TransferenciasController transferenciasController;
    @InjectMocks
    TransferenciasService transferenciasService;
    @Mock
    IRepositorioTransferencias repositorioTransferencias;
    @Mock
    IRepositorioClientes repositorioCliente;
    @Mock
    IRepositorioConta repositorioConta;
    
    @BeforeEach
    public void setaService(){
        this.transferenciasController= new TransferenciasController(transferenciasService);
    }


    @Test
    public void deveriaSucessoAoRealizarTransferencia() throws DominioException {
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(geraTransferencia(2L,1L));
        given(repositorioCliente.findByContaId(2L)).willReturn(Optional.of(geraCliente(2L)));
        given(repositorioCliente.findByContaId(1L)).willReturn(Optional.of(geraCliente(1L)));
        
        assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(mensagemSucessoTransferencia("Bruno", "Bruno", "10.0")),
                transferenciasController.realizarTransferencia(transferenciaDTO));
    }

    @Test
    public void deveriaDarErroDeValorMaiorQueMilAoRealizarTransferencia() throws DominioException {
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(geraTransferencia(geraConta(2L),geraConta(1L),
                ResultadoTransferenciaEnum.FALHA.getResultado(), 1000.1));
        Optional<Cliente> clienteOrigem = Optional.of(geraCliente(2L));
        clienteOrigem.get().getConta().setSaldo(2000.0);
        given(repositorioCliente.findByContaId(2L)).willReturn(clienteOrigem);
        given(repositorioCliente.findByContaId(1L)).willReturn(Optional.of(geraCliente(1L)));
        
        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constantes.VALOR_MAIOR),
                transferenciasController.realizarTransferencia(transferenciaDTO));

    }

    @Test
    public void deveriaDarErroDeContaOrigemInexistenteAoRealizarTransferencia() {
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(geraTransferencia(2L,1L));
        given(repositorioCliente.findByContaId(2L)).willReturn(Optional.empty());
        given(repositorioCliente.findByContaId(1L)).willReturn(Optional.of(geraCliente(1L)));

        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constantes.CONTA_ORIGEM_INEXISTENTE),
                transferenciasController.realizarTransferencia(transferenciaDTO));
    }

    @Test
    public void deveriaDarErroDeContaDestinoInexistenteAoRealizarTransferencia(){
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(geraTransferencia(2L,1L));
        given(repositorioCliente.findByContaId(2L)).willReturn(Optional.of(geraCliente(2L)));
        given(repositorioCliente.findByContaId(1L)).willReturn(Optional.empty());

        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constantes.CONTA_DESTINO_INEXISTENTE),
                transferenciasController.realizarTransferencia(transferenciaDTO));
    }

    @Test
    public void deveriaDarErroDeContasInexistentesAoRealizarTransferencia(){
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(geraTransferencia(2L,1L));
        given(repositorioCliente.findByContaId(2L)).willReturn(Optional.empty());
        given(repositorioCliente.findByContaId(1L)).willReturn(Optional.empty());

        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constantes.CONTAS_INEXISTENTES),
                transferenciasController.realizarTransferencia(transferenciaDTO));
    }

    @Test
    public void deveriaDarErroComValorMenorQueZero(){
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(geraTransferencia(geraConta(2L),geraConta(1L),
                ResultadoTransferenciaEnum.FALHA.getResultado(), -90.0));
        given(repositorioCliente.findByContaId(2L)).willReturn(Optional.of(geraCliente(2L)));
        given(repositorioCliente.findByContaId(1L)).willReturn(Optional.of(geraCliente(1L)));

        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constantes.VALOR_INVALIDO),
                transferenciasController.realizarTransferencia(transferenciaDTO));
    }

    @Test
    public void deveriaDarErroDeSaldoInsuficiente(){
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(geraTransferencia(geraConta(2L),geraConta(1L),
                ResultadoTransferenciaEnum.FALHA.getResultado(), 1000.0));
        Optional<Cliente> clienteOrigem = Optional.of(geraCliente(2L));
        clienteOrigem.get().getConta().setSaldo(100.0);
        given(repositorioCliente.findByContaId(2L)).willReturn(clienteOrigem);
        given(repositorioCliente.findByContaId(1L)).willReturn(Optional.of(geraCliente(1L)));

        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constantes.SALDO_INCUFICIENTE),
                transferenciasController.realizarTransferencia(transferenciaDTO));
    }

    @Test
    public void deveriaSucessoAoListarTransferenciasPorConta() {
        List<Transferencia> transferencias = new ArrayList<>();
        transferencias.add(geraTransferencia());
        transferencias.add(geraTransferencia());

        given(repositorioTransferencias.findByContaOrigemId(2L)).willReturn(transferencias);

        assertEquals(ResponseEntity.ok(transferencias).getStatusCode(), transferenciasController.listaTransferencias(2L).getStatusCode());
        assertLinesMatch(transferenciasController.listaTransferencias(2L).getBody().stream()
                .map(t->t.getNumeroContaDestino().toString()).collect(Collectors.toList()),
                transferencias.stream().map(t->t.getContaDestino().getId().toString()).collect(Collectors.toList()));
        assertLinesMatch(transferenciasController.listaTransferencias(2L).getBody().stream()
                        .map(t->t.getNumeroContaOrigem().toString()).collect(Collectors.toList()),
                transferencias.stream().map(t->t.getContaOrigem().getId().toString()).collect(Collectors.toList()));

    }

    @Test
    public void deveriaTrazerResultadoVazioAoAcessarTransferenciasPorConta() {
        assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT).body(null),
                transferenciasController.listaTransferencias(2L));

    }
}
