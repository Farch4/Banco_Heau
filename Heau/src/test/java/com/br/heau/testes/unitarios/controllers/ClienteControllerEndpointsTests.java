package com.br.heau.testes.unitarios.controllers;

import com.br.heau.controller.ClienteController;
import com.br.heau.model.dto.ClienteDTO;
import com.br.heau.service.ClienteService;
import com.br.heau.testes.ITestCasesClientes;
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
import java.util.Optional;

import static com.br.heau.testes.utils.GeradoraDeEntidades.geraCliente;
import static com.br.heau.testes.utils.GeradoraDeEntidades.geraClienteDTO;
import static com.br.heau.util.GeradoraDeMensagens.mensagemSucessoCadastroCliente;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class ClienteControllerEndpointsTests implements ITestCasesClientes {

    @Mock
    ClienteService clienteService;

    @InjectMocks
    ClienteController clienteController;

    @Test
    public void deveriaTerSucessoAoCadastrarCliente() throws DominioException {
        ClienteDTO cliente = geraClienteDTO();
        when(clienteService.cadastroCliente(cliente)).
                thenReturn(mensagemSucessoCadastroCliente(cliente.getNome(),
                        cliente.getNumeroContaOpcional().toString()));

        assertEquals(ResponseEntity.status(HttpStatus.CREATED)
                .body(mensagemSucessoCadastroCliente(cliente.getNome(), cliente.getNumeroContaOpcional().toString()))
                , clienteController.cadastraCliente(cliente));

    }

    @Test
    public void deveriaFalharAoCadastrarClienteComNomeInvalido() throws DominioException {
        ClienteDTO cliente = geraClienteDTO("3bruno", 2.0);
        when(clienteService.cadastroCliente(cliente)).
                thenThrow(new DominioException(Constantes.USUARIO_CADASTRO_USUARIO_INVALIDO));

        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Constantes.USUARIO_CADASTRO_USUARIO_INVALIDO)
                , clienteController.cadastraCliente(cliente));

    }

    @Test
    public void deveriaFalharAoCadastrarClienteComNomeVazio() throws DominioException {
        ClienteDTO cliente = geraClienteDTO("",200.0);
        when(clienteService.cadastroCliente(cliente)).
                thenThrow(new DominioException(Constantes.USUARIO_CADASTRO_USUARIO_INVALIDO));

        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Constantes.USUARIO_CADASTRO_USUARIO_INVALIDO)
                , clienteController.cadastraCliente(cliente));

    }

    @Test
    public void deveriaFalharAoCadastrarClienteComSaldoMenorQueZero() throws DominioException {
        ClienteDTO cliente = geraClienteDTO("Bruno", -9.0);
        when(clienteService.cadastroCliente(cliente)).
                thenThrow(new DominioException(Constantes.SALDO_INVALIDO));

        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Constantes.SALDO_INVALIDO)
                , clienteController.cadastraCliente(cliente));

    }

    @Test
    public void deveriaTerSucessoAoListarClientes()  {

        List clientes = new ArrayList<>();

        clientes.add(geraCliente(1L));
        clientes.add(geraCliente(2L));

        when(clienteService.ListaClientes()).
                thenReturn(clientes);

        assertEquals(ResponseEntity.status(HttpStatus.OK)
                        .body(clientes) ,clienteController.listaClientes());

    }

    @Test
    public void deveriaRetornarListaVazia() {
        List clientes = new ArrayList<>();

        when(clienteService.ListaClientes()).
                thenReturn(clientes);

        assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(null) ,clienteController.listaClientes());

    }

    @Test
    public void deveriaTerSucessoAoBuscarClientePorConta(){

        when(clienteService.buscaPelaConta(1L)).
                thenReturn(Optional.of(geraCliente(1L)));

        assertEquals(ResponseEntity.status(HttpStatus.OK)
                .body(Optional.of(geraCliente(1L))) ,clienteController.buscaPelaConta(1L));

    }

    @Test
    public void deveriaNaoEncontrarClientePorConta(){

        when(clienteService.buscaPelaConta(1L)).
                thenReturn(Optional.empty());

        assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(Optional.empty()) ,clienteController.buscaPelaConta(1L));

    }
}
