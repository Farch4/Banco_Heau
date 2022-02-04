package com.br.heau.testes.unitarios.servicos;

import com.br.heau.data.IRepositorioClientes;
import com.br.heau.model.Cliente;
import com.br.heau.model.Conta;
import com.br.heau.model.dto.ClienteDTO;
import com.br.heau.service.ClienteService;
import com.br.heau.util.Constantes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class ClienteServiceMetodosTests {

    ClienteService clienteService;

    @Mock
    IRepositorioClientes repositorioClientes;

    @BeforeEach
    public void beforeEach() {
        this.clienteService= new ClienteService(repositorioClientes);
    }

    @Test
    public void testeDeveriaSucessoCadastroCliente(){
        String nome = "Bruno";
        Long conta = 1L;
        given(repositorioClientes.save(new Cliente(new ClienteDTO(nome, 5000.0, conta)))).willReturn(new Cliente(new ClienteDTO(nome, 5000.0, conta)));
        assertEquals(nome+Constantes.USUARIO_CADASTRO_SUCESSO+conta, clienteService.cadastroCliente(new ClienteDTO(nome, 5000.0, conta)));
    }

    @Test
    public void testeDeveFalharGenericamenteCadastroCliente(){
        given(repositorioClientes.save(null)).willReturn(Constantes.USUARIO_CADASTRO_FALHA_GENERICA);
        assertEquals(Constantes.USUARIO_CADASTRO_FALHA_GENERICA, clienteService.cadastroCliente(null));
    }
    
    @Test
    public void testeDeveTerSucessoAoListarClientes(){
        List<Cliente> listaMock = new ArrayList<>();
        listaMock.add(new Cliente((new ClienteDTO("Bruno", 5000.0, 1L))));
        listaMock.add(new Cliente((new ClienteDTO("Sergio", 8000.0, 2L))));
        given(repositorioClientes.findAll()).willReturn(listaMock);
        List<Cliente>listaRetorno = clienteService.ListaClientes();
        assertLinesMatch(listaRetorno.stream().map(cliente->cliente.getConta().getId().toString()).collect(Collectors.toList()),
                listaMock.stream().map(cliente -> cliente.getConta().getId().toString()).collect(Collectors.toList()));
    }

    @Test
    public void testeDeveRetornarListaVazia(){
        given(repositorioClientes.findAll()).willReturn(null);
        assertNull(clienteService.ListaClientes());
    }

    @Test
    public void testeDeveTerSucessoEmPesquisarUsuarioPelaConta(){
        Optional<Cliente> clienteResposta = Optional.of(new Cliente());
        clienteResposta.get().setConta(new Conta(1L));
        given(repositorioClientes.findByContaId(1L)).willReturn(clienteResposta);
        assertEquals(clienteService.buscaPelaConta(1L).get().getConta().getId(), 1L);
    }

    @Test
    public void testeDeveTrazeResultadoVazio(){
        given(repositorioClientes.findByContaId(1L)).willReturn(null);
        assertNull(clienteService.buscaPelaConta(1L));
    }

}
