package com.br.heau.testes.unitarios.servicos;

import com.br.heau.repository.IRepositorioClientes;
import com.br.heau.model.Cliente;
import com.br.heau.model.dto.ClienteDTO;
import com.br.heau.service.ClienteService;
import com.br.heau.testes.ITestCasesClientes;
import com.br.heau.util.Constantes;
import com.br.heau.util.exception.DominioException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.br.heau.testes.utils.GeradoraDeEntidades.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static com.br.heau.util.GeradoraDeMensagens.mensagemSucessoCadastroCliente;

@SpringBootTest
public class ClienteServiceMetodosTests implements ITestCasesClientes {

    @Mock
    IRepositorioClientes repositorioClientes;

    @InjectMocks
    ClienteService clienteService;

    @Test
    public void deveriaTerSucessoAoCadastrarCliente() throws DominioException {
        String nome = "Bruno";
        Long conta = 1L;
        given(repositorioClientes.save(geraCliente(1L))).willReturn(geraCliente(1L));
        assertEquals(mensagemSucessoCadastroCliente(nome,conta.toString()), clienteService.cadastroCliente(new ClienteDTO(nome, 5000.0, conta)));
    }

    @Test
    public void deveriaFalharAoCadastrarClienteComNomeVazio(){
        String nome = "";
        Long conta = 1L;
        DominioException exception = assertThrows(DominioException.class, ()->clienteService.cadastroCliente(new ClienteDTO(nome, 5000.0, conta)));
        assertEquals(Constantes.USUARIO_CADASTRO_USUARIO_INVALIDO, exception.getMensagensDeErro());
    }

    @Test
    public void deveriaFalharAoCadastrarClienteComNomeInvalido(){
        String nome = "8Deivid";
        Long conta = 1L;
        DominioException exception = assertThrows(DominioException.class, ()->clienteService.cadastroCliente(new ClienteDTO(nome, 5000.0, conta)));
        assertEquals(Constantes.USUARIO_CADASTRO_USUARIO_INVALIDO, exception.getMensagensDeErro());
    }

    @Test
    public void deveriaFalharAoCadastrarClienteComSaldoMenorQueZero(){
        String nome = "Sergio";
        Long conta = 1L;
        DominioException exception = assertThrows(DominioException.class, ()->clienteService.cadastroCliente(new ClienteDTO(nome, -2.0, conta)));
        assertEquals(Constantes.SALDO_INVALIDO, exception.getMensagensDeErro());
    }

    @Test
    public void deveriaTerSucessoAoListarClientes(){
        List<Cliente> listaMock = new ArrayList<>();
        listaMock.add(geraCliente(1L));
        listaMock.add(geraCliente(2L));
        given(repositorioClientes.findAll()).willReturn(listaMock);
        assertEquals(listaMock, clienteService.ListaClientes());
    }

    @Test
    public void deveriaRetornarListaVazia(){
        given(repositorioClientes.findAll()).willReturn(null);
        assertNull(clienteService.ListaClientes());
    }

    @Test
    public void deveriaTerSucessoAoBuscarClientePorConta(){
        Optional<Cliente> clienteResposta = Optional.of(geraCliente(1L));
        given(repositorioClientes.findByContaId(1L)).willReturn(clienteResposta);
        assertEquals(clienteService.buscaPelaConta(1L).get().getConta().getId(), 1L);
    }

    @Test
    public void deveriaNaoEncontrarClientePorConta(){
        given(repositorioClientes.findByContaId(1L)).willReturn(null);
        assertNull(clienteService.buscaPelaConta(1L));
    }



}
