package com.br.heau.testes.unitarios.servicos;

import com.br.heau.model.enums.ResultadoTransferenciaEnum;
import com.br.heau.repository.IRepositorioClientes;
import com.br.heau.repository.IRepositorioConta;
import com.br.heau.repository.IRepositorioTransferencias;
import com.br.heau.model.Cliente;
import com.br.heau.model.Conta;
import com.br.heau.model.Transferencia;
import com.br.heau.model.dto.TransferenciaDTO;
import com.br.heau.service.TransferenciasService;
import com.br.heau.testes.ITestCasesTransferencias;
import com.br.heau.util.Constantes;
import com.br.heau.util.exception.DominioException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.br.heau.testes.utils.GeradoraDeEntidades.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static com.br.heau.util.GeradoraDeMensagens.mensagemSucessoTransferencia;


@SpringBootTest
public class TransferenciaMetodosServiceTests implements ITestCasesTransferencias {

    @Mock
    IRepositorioClientes repositorioClientes;

    @Mock
    IRepositorioTransferencias repositorioTransferencias;

    @Mock
    IRepositorioConta repositorioConta;

    @InjectMocks
    TransferenciasService transferenciasService;


    @Test
    public void deveriaSucessoAoRealizarTransferencia() throws DominioException {

        Cliente clienteOrigem= geraCliente(2L);
        Cliente clienteDestino = geraCliente(1L);
        Transferencia transferencia = geraTransferencia(clienteOrigem.getConta(), clienteDestino.getConta(),
                ResultadoTransferenciaEnum.SUCESSO.getResultado());

        given(repositorioClientes.findByContaId(2L)).willReturn(Optional.of(clienteOrigem));
        given(repositorioClientes.findByContaId(1L)).willReturn(Optional.of(clienteDestino));
        given(repositorioTransferencias.save(transferencia)).willReturn(transferencia);

        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(transferencia);
        assertEquals(mensagemSucessoTransferencia(clienteOrigem.getNome(), clienteDestino.getNome(), transferenciaDTO.getValorTransferencia().toString()),
                transferenciasService.realizaTransferencia(transferenciaDTO));
    }

    @Test
    public void deveriaDarErroDeValorMaiorQueMilAoRealizarTransferencia(){

        Cliente clienteOrigem= geraCliente(2L, 2000.0);
        Cliente clienteDestino = geraCliente(1L);
        Transferencia transferencia = geraTransferencia(clienteOrigem.getConta(), clienteDestino.getConta(),
                Constantes.VALOR_MAIOR, 1500.0);

        given(repositorioClientes.findByContaId(2L)).willReturn(Optional.of(clienteOrigem));
        given(repositorioClientes.findByContaId(1L)).willReturn(Optional.of(clienteDestino));
        given(repositorioTransferencias.save(transferencia)).willReturn(transferencia);

        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(transferencia);

        DominioException exception = assertThrows(DominioException.class, ()->
                transferenciasService.realizaTransferencia(transferenciaDTO));

        assertEquals(Constantes.VALOR_MAIOR, exception.getMensagensDeErro());

    }

    @Test
    public void deveriaDarErroDeContaOrigemInexistenteAoRealizarTransferencia(){

        Cliente clienteOrigem= geraCliente(2L);
        Cliente clienteDestino = geraCliente(1L);
        Transferencia transferencia = geraTransferencia(clienteOrigem.getConta(), clienteDestino.getConta(),
                Constantes.CONTA_ORIGEM_INEXISTENTE, 150.0);

        given(repositorioClientes.findByContaId(2L)).willReturn(Optional.empty());
        given(repositorioClientes.findByContaId(1L)).willReturn(Optional.of(clienteDestino));
        given(repositorioTransferencias.save(transferencia)).willReturn(transferencia);

        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(transferencia);

        DominioException exception = assertThrows(DominioException.class, ()->
                transferenciasService.realizaTransferencia(transferenciaDTO));

        assertEquals(Constantes.CONTA_ORIGEM_INEXISTENTE, exception.getMensagensDeErro());

    }

    @Test
    public void deveriaDarErroDeContaDestinoInexistenteAoRealizarTransferencia(){

        Cliente clienteOrigem= geraCliente(2L);
        Cliente clienteDestino = geraCliente(1L);
        Transferencia transferencia = geraTransferencia(clienteOrigem.getConta(), clienteDestino.getConta(),
                Constantes.CONTA_DESTINO_INEXISTENTE, 100.0);

        given(repositorioClientes.findByContaId(2L)).willReturn(Optional.of(clienteOrigem));
        given(repositorioClientes.findByContaId(1L)).willReturn(Optional.empty());
        given(repositorioTransferencias.save(transferencia)).willReturn(transferencia);

        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(transferencia);

        DominioException exception = assertThrows(DominioException.class, ()->
                transferenciasService.realizaTransferencia(transferenciaDTO));

        assertEquals(Constantes.CONTA_DESTINO_INEXISTENTE, exception.getMensagensDeErro());

    }

    @Test
    public void deveriaDarErroDeContasInexistentesAoRealizarTransferencia(){

        Cliente clienteOrigem= geraCliente(2L);
        Cliente clienteDestino = geraCliente(1L);
        Transferencia transferencia = geraTransferencia(clienteOrigem.getConta(), clienteDestino.getConta(),
                Constantes.CONTAS_INEXISTENTES, 150.0);

        given(repositorioClientes.findByContaId(2L)).willReturn(Optional.empty());
        given(repositorioClientes.findByContaId(1L)).willReturn(Optional.empty());
        given(repositorioTransferencias.save(transferencia)).willReturn(transferencia);

        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(transferencia);

        DominioException exception = assertThrows(DominioException.class, ()->
                transferenciasService.realizaTransferencia(transferenciaDTO));

        assertEquals(Constantes.CONTAS_INEXISTENTES, exception.getMensagensDeErro());

    }

    @Test
    public void deveriaDarErroComValorMenorQueZero() {

        Cliente clienteOrigem= geraCliente(2L);
        Cliente clienteDestino = geraCliente(1L);
        Transferencia transferencia = geraTransferencia(clienteOrigem.getConta(), clienteDestino.getConta(),
                ResultadoTransferenciaEnum.SUCESSO.getResultado(), -2.00);

        given(repositorioClientes.findByContaId(2L)).willReturn(Optional.of(clienteOrigem));
        given(repositorioClientes.findByContaId(1L)).willReturn(Optional.of(clienteDestino));
        given(repositorioTransferencias.save(transferencia)).willReturn(transferencia);

        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(transferencia);
        DominioException exception = assertThrows(DominioException.class, ()->
                transferenciasService.realizaTransferencia(transferenciaDTO));

        assertEquals(Constantes.VALOR_INVALIDO, exception.getMensagensDeErro());

    }

    @Test
    public void deveriaDarErroDeSaldoInsuficiente() {

        Cliente clienteOrigem= geraCliente(2L);
        Cliente clienteDestino = geraCliente(1L);
        Transferencia transferencia = geraTransferencia(clienteOrigem.getConta(), clienteDestino.getConta(),
                Constantes.SALDO_INCUFICIENTE, 300.0);

        given(repositorioClientes.findByContaId(2L)).willReturn(Optional.of(clienteOrigem));
        given(repositorioClientes.findByContaId(1L)).willReturn(Optional.of(clienteDestino));
        given(repositorioTransferencias.save(transferencia)).willReturn(transferencia);

        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(transferencia);
        DominioException exception = assertThrows(DominioException.class, ()->
                transferenciasService.realizaTransferencia(transferenciaDTO));

        assertEquals(Constantes.SALDO_INCUFICIENTE, exception.getMensagensDeErro());

    }

    @Test
    public void deveriaSucessoAoListarTransferenciasPorConta(){

        Conta contaOrigem= geraConta(2L);
        Conta contaDestino = geraConta(1L);

        Transferencia transferencia1 = geraTransferencia(1L, contaOrigem, contaDestino, ResultadoTransferenciaEnum.SUCESSO.getResultado());

        Transferencia transferencia2 = geraTransferencia(2L, contaOrigem, contaDestino, ResultadoTransferenciaEnum.SUCESSO.getResultado());
        List<Transferencia> transferencias = new ArrayList<>();

        transferencias.add(transferencia1);
        transferencias.add(transferencia2);

        given(repositorioTransferencias.findByContaOrigemId(2L)).willReturn(transferencias);

        assertLinesMatch(transferencias.stream().map(t->t.getId().toString()).collect(Collectors.toList()),
                transferenciasService.listaTransferenciasPorConta(2L).stream().map(t->t.getId().toString()).collect(Collectors.toList()));
        assertLinesMatch(transferencias.stream().map(Transferencia::getResultado).collect(Collectors.toList()),
                transferenciasService.listaTransferenciasPorConta(2L).stream().map(TransferenciaDTO::getResultado).collect(Collectors.toList()));
    }

    @Test
    public void deveriaTrazerResultadoVazioAoAcessarTransferenciasPorConta(){

        List listaVazia = new ArrayList<>();
        given(repositorioTransferencias.findByContaOrigemId(2L)).willReturn(listaVazia);

        assertEquals(listaVazia.size(), transferenciasService.listaTransferenciasPorConta(2L).size());

    }
}
