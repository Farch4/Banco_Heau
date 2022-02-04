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
import static com.br.heau.testes.utils.EntityFactory.*;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;

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
        given(repositorioClientes.save(geraCliente(1L))).willReturn(geraCliente(1L));
        assertEquals(nome+Constantes.USUARIO_CADASTRO_SUCESSO+conta, clienteService.cadastroCliente(new ClienteDTO(nome, 5000.0, conta)));
    }

    @Test
    public void testeDeveTerSucessoAoListarClientes(){
        List<Cliente> listaMock = new ArrayList<>();
        listaMock.add(geraCliente(1L));
        listaMock.add(geraCliente(2L));
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
        Optional<Cliente> clienteResposta = Optional.of(geraCliente(1L));
        given(repositorioClientes.findByContaId(1L)).willReturn(clienteResposta);
        assertEquals(clienteService.buscaPelaConta(1L).get().getConta().getId(), 1L);
    }

    @Test
    public void testeDeveTrazeResultadoVazio(){
        given(repositorioClientes.findByContaId(1L)).willReturn(null);
        assertNull(clienteService.buscaPelaConta(1L));
    }

}
