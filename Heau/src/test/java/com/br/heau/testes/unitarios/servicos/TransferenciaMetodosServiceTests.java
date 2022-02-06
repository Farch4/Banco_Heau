package com.br.heau.testes.unitarios.servicos;

import com.br.heau.data.IRepositorioClientes;
import com.br.heau.data.IRepositorioConta;
import com.br.heau.data.IRepositorioTransferencias;
import com.br.heau.model.Cliente;
import com.br.heau.model.Conta;
import com.br.heau.model.Transferencia;
import com.br.heau.model.dto.TransferenciaDTO;
import com.br.heau.model.enums.ResultadoTransferenciaEnum;
import com.br.heau.service.TransferenciasService;
import com.br.heau.util.excecao.DominioException;
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
public class TransferenciaMetodosServiceTests {

    @Mock
    IRepositorioClientes repositorioClientes;

    @Mock
    IRepositorioTransferencias repositorioTransferencias;

    @Mock
    IRepositorioConta repositorioConta;

    @InjectMocks
    TransferenciasService transferenciasService;


    @Test
    public void testeDeveriaSucessoAoRealizarTransferencia() throws DominioException {

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
    public void testeDeveriaDarErroDeValorMaiorQueMilAoRealizarTransferencia(){

        Cliente clienteOrigem= geraCliente(2L);
        Cliente clienteDestino = geraCliente(1L);
        Transferencia transferencia = geraTransferencia(clienteOrigem.getConta(), clienteDestino.getConta(),
                ResultadoTransferenciaEnum.VALOR_MAIOR.getResultado(), 1500.0);

        given(repositorioClientes.findByContaId(2L)).willReturn(Optional.of(clienteOrigem));
        given(repositorioClientes.findByContaId(1L)).willReturn(Optional.of(clienteDestino));
        given(repositorioTransferencias.save(transferencia)).willReturn(transferencia);

        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(transferencia);

        DominioException exception = assertThrows(DominioException.class, ()->
                transferenciasService.realizaTransferencia(transferenciaDTO));

        assertEquals(ResultadoTransferenciaEnum.VALOR_MAIOR.getResultado(), exception.getMensagensDeErro());

    }

    @Test
    public void testeDeveriaDarErroDeContaOrigemInexistenteAoRealizarTransferencia(){

        Cliente clienteOrigem= geraCliente(2L);
        Cliente clienteDestino = geraCliente(1L);
        Transferencia transferencia = geraTransferencia(clienteOrigem.getConta(), clienteDestino.getConta(),
                ResultadoTransferenciaEnum.CONTA_ORIGEM_INEXISTENTE.getResultado(), 150.0);

        given(repositorioClientes.findByContaId(2L)).willReturn(Optional.empty());
        given(repositorioClientes.findByContaId(1L)).willReturn(Optional.of(clienteDestino));
        given(repositorioTransferencias.save(transferencia)).willReturn(transferencia);

        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(transferencia);

        DominioException exception = assertThrows(DominioException.class, ()->
                transferenciasService.realizaTransferencia(transferenciaDTO));

        assertEquals(ResultadoTransferenciaEnum.CONTA_ORIGEM_INEXISTENTE.getResultado(), exception.getMensagensDeErro());

    }

    @Test
    public void testeDeveriaDarErroDeContaDestinoInexistenteAoRealizarTransferencia(){

        Cliente clienteOrigem= geraCliente(2L);
        Cliente clienteDestino = geraCliente(1L);
        Transferencia transferencia = geraTransferencia(clienteOrigem.getConta(), clienteDestino.getConta(),
                ResultadoTransferenciaEnum.CONTA_DESTINO_INEXISTENTE.getResultado(), 150.0);

        given(repositorioClientes.findByContaId(2L)).willReturn(Optional.of(clienteOrigem));
        given(repositorioClientes.findByContaId(1L)).willReturn(Optional.empty());
        given(repositorioTransferencias.save(transferencia)).willReturn(transferencia);

        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(transferencia);

        DominioException exception = assertThrows(DominioException.class, ()->
                transferenciasService.realizaTransferencia(transferenciaDTO));

        assertEquals(ResultadoTransferenciaEnum.CONTA_DESTINO_INEXISTENTE.getResultado(), exception.getMensagensDeErro());

    }

    @Test
    public void testeDeveriaDarErroDeContasInexistentesAoRealizarTransferencia(){

        Cliente clienteOrigem= geraCliente(2L);
        Cliente clienteDestino = geraCliente(1L);
        Transferencia transferencia = geraTransferencia(clienteOrigem.getConta(), clienteDestino.getConta(),
                ResultadoTransferenciaEnum.CONTAS_INEXISTENTES.getResultado(), 150.0);

        given(repositorioClientes.findByContaId(2L)).willReturn(Optional.empty());
        given(repositorioClientes.findByContaId(1L)).willReturn(Optional.empty());
        given(repositorioTransferencias.save(transferencia)).willReturn(transferencia);

        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(transferencia);

        DominioException exception = assertThrows(DominioException.class, ()->
                transferenciasService.realizaTransferencia(transferenciaDTO));

        assertEquals(ResultadoTransferenciaEnum.CONTAS_INEXISTENTES.getResultado(), exception.getMensagensDeErro());

    }

    @Test
    public void testeDeveriaDarErroComValorMenorQueZero() {

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

        assertEquals(ResultadoTransferenciaEnum.VALOR_INVALIDO.getResultado(), exception.getMensagensDeErro());

    }

    @Test
    public void testeDeveriaDarErroDeSaldoInsuficiente() {

        Cliente clienteOrigem= geraCliente(2L);
        Cliente clienteDestino = geraCliente(1L);
        Transferencia transferencia = geraTransferencia(clienteOrigem.getConta(), clienteDestino.getConta(),
                ResultadoTransferenciaEnum.SALDO_INCUFICIENTE.getResultado(), 300.0);

        given(repositorioClientes.findByContaId(2L)).willReturn(Optional.of(clienteOrigem));
        given(repositorioClientes.findByContaId(1L)).willReturn(Optional.of(clienteDestino));
        given(repositorioTransferencias.save(transferencia)).willReturn(transferencia);

        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(transferencia);
        DominioException exception = assertThrows(DominioException.class, ()->
                transferenciasService.realizaTransferencia(transferenciaDTO));

        assertEquals(ResultadoTransferenciaEnum.SALDO_INCUFICIENTE.getResultado(), exception.getMensagensDeErro());

    }

    @Test
    public void testeDeveriaSucessoAoListarTransferenciasPorConta(){

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
    public void testeDeveriaTrazerResultadoVazioAoAcessarTransferenciasPorConta(){

        List listaVazia = new ArrayList<>();
        given(repositorioTransferencias.findByContaOrigemId(2L)).willReturn(listaVazia);

        assertEquals(listaVazia.size(), transferenciasService.listaTransferenciasPorConta(2L).size());

    }
}
