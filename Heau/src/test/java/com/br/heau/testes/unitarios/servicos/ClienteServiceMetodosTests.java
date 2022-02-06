package com.br.heau.testes.unitarios.servicos;

import com.br.heau.data.IRepositorioClientes;
import com.br.heau.model.Cliente;
import com.br.heau.model.dto.ClienteDTO;
import com.br.heau.service.ClienteService;
import com.br.heau.util.Constantes;
import com.br.heau.util.excecao.DominioException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static com.br.heau.testes.utils.GeradoraDeEntdades.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static com.br.heau.util.GeradoraDeMensagens.mensagemSucessoCadastroCliente;

@SpringBootTest
public class ClienteServiceMetodosTests {

    @Mock
    IRepositorioClientes repositorioClientes;

    @InjectMocks
    ClienteService clienteService;

    @Test
    public void testeDeveriaSucessoCadastroCliente() throws DominioException {
        String nome = "Bruno";
        Long conta = 1L;
        given(repositorioClientes.save(geraCliente(1L))).willReturn(geraCliente(1L));
        assertEquals(mensagemSucessoCadastroCliente(nome,conta.toString()), clienteService.cadastroCliente(new ClienteDTO(nome, 5000.0, conta)));
    }

    @Test
    public void testeDeveApresentarErroDeNomeInvalidoParaNomeVazio(){
        String nome = "";
        Long conta = 1L;
        DominioException exception = assertThrows(DominioException.class, ()->clienteService.cadastroCliente(new ClienteDTO(nome, 5000.0, conta)));
        assertEquals(Constantes.USUARIO_CADASTRO_USUARIO_INVALIDO, exception.getMensagensDeErro());
    }

    @Test
    public void testeDeveApresentarErroDeNomeInvalidoParaNomeComNumero(){
        String nome = "8Deivid";
        Long conta = 1L;
        DominioException exception = assertThrows(DominioException.class, ()->clienteService.cadastroCliente(new ClienteDTO(nome, 5000.0, conta)));
        assertEquals(Constantes.USUARIO_CADASTRO_USUARIO_INVALIDO, exception.getMensagensDeErro());
    }

    @Test
    public void testeDeveApresentarErroDeSaldoMenorQueZero(){
        String nome = "Sergio";
        Long conta = 1L;
        DominioException exception = assertThrows(DominioException.class, ()->clienteService.cadastroCliente(new ClienteDTO(nome, -2.0, conta)));
        assertEquals(Constantes.SALDO_INVALIDO, exception.getMensagensDeErro());
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
