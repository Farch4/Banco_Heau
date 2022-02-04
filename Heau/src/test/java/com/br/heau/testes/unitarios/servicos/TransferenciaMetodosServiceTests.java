package com.br.heau.testes.unitarios.servicos;

import com.br.heau.data.IRepositorioClientes;
import com.br.heau.data.IRepositorioConta;
import com.br.heau.data.IRepositorioTransferencias;
import com.br.heau.model.Cliente;
import com.br.heau.model.Transferencia;
import com.br.heau.model.dto.ClienteDTO;
import com.br.heau.model.dto.TransferenciaDTO;
import com.br.heau.model.enums.ResultadoTransferenciaEnum;
import com.br.heau.service.ClienteService;
import com.br.heau.service.TransferenciasService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class TransferenciaMetodosServiceTests {

    TransferenciasService transferenciasService;

    @Mock
    IRepositorioClientes repositorioClientes;
    @Mock
    IRepositorioConta repositorioConta;
    @Mock
    IRepositorioTransferencias repositorioTransferencias;

    @BeforeEach
    public void beforeEach() {
        this.transferenciasService=
                new TransferenciasService(repositorioClientes,repositorioConta, repositorioTransferencias);
    }

    @Test
    public void testeDeveriaSucessoAoRealizarTransferencia(){

        Cliente clienteOrigem= new Cliente((new ClienteDTO("Sergio", 8000.0, 2L)));
        Cliente clienteDestino = new Cliente((new ClienteDTO("Bruno", 5000.0, 1L)));
        Transferencia transferencia = new Transferencia(clienteOrigem.getConta(), clienteDestino.getConta(),
                500.0, ResultadoTransferenciaEnum.SUCESSO.getResultado());

        given(repositorioClientes.findByContaId(2L)).willReturn(Optional.of(clienteOrigem));
        given(repositorioClientes.findByContaId(1L)).willReturn(Optional.of(clienteDestino));
        given(repositorioTransferencias.save(transferencia)).willReturn(transferencia);

        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(transferencia);
        assertEquals(transferenciasService.realizaTransferencia(transferenciaDTO).getResultado(), ResultadoTransferenciaEnum.SUCESSO.getResultado());
    }

    @Test
    public void testeDeveriaDarErroDeValorMaiorQueMilAoRealizarTransferencia(){

        Cliente clienteOrigem= new Cliente((new ClienteDTO("Sergio", 8000.0, 2L)));
        Cliente clienteDestino = new Cliente((new ClienteDTO("Bruno", 5000.0, 1L)));
        Transferencia transferencia = new Transferencia(clienteOrigem.getConta(), clienteDestino.getConta(),
                1500.0, ResultadoTransferenciaEnum.VALOR_MAIOR.getResultado());

        given(repositorioClientes.findByContaId(2L)).willReturn(Optional.of(clienteOrigem));
        given(repositorioClientes.findByContaId(1L)).willReturn(Optional.of(clienteDestino));
        given(repositorioTransferencias.save(transferencia)).willReturn(transferencia);

        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(transferencia);
        assertEquals(transferenciasService.realizaTransferencia(transferenciaDTO).getResultado(), ResultadoTransferenciaEnum.VALOR_MAIOR.getResultado());
    }

    @Test
    public void testeDeveriaSucessoAoListarTransferenciasPorConta(){

        Cliente clienteOrigem= new Cliente((new ClienteDTO("Sergio", 8000.0, 2L)));
        Cliente clienteDestino = new Cliente((new ClienteDTO("Bruno", 5000.0, 1L)));
        Transferencia transferencia1 = new Transferencia(1L, 0, clienteOrigem.getConta(), clienteDestino.getConta(),
                150.0, ResultadoTransferenciaEnum.SUCESSO.getResultado(), new Date(System.currentTimeMillis()));

        Transferencia transferencia2 = new Transferencia(2L, 0, clienteOrigem.getConta(), clienteDestino.getConta(),
                150.0, ResultadoTransferenciaEnum.SUCESSO.getResultado(), new Date(System.currentTimeMillis()));
        List<Transferencia> transferencias = new ArrayList<>();

        transferencias.add(transferencia1);
        transferencias.add(transferencia2);

        given(repositorioTransferencias.findByContaOrigemId(2L)).willReturn(transferencias);

        assertLinesMatch(transferencias.stream().map(t->t.getId().toString()).collect(Collectors.toList()),
                transferenciasService.listaTransferenciasPorConta(2L).stream().map(t->t.getId().toString()).collect(Collectors.toList()));
        assertLinesMatch(transferencias.stream().map(t->t.getResultado()).collect(Collectors.toList()),
                transferenciasService.listaTransferenciasPorConta(2L).stream().map(t->t.getResultado()).collect(Collectors.toList()));


    }
}
