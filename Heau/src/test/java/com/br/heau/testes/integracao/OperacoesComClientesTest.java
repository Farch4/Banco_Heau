package com.br.heau.testes.integracao;
import com.br.heau.controller.ClienteController;
import com.br.heau.model.Cliente;
import com.br.heau.repository.IRepositorioClientes;
import com.br.heau.service.ClienteService;
import com.br.heau.testes.ITestCasesClientes;
import com.br.heau.util.Constantes;
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
import static com.br.heau.testes.utils.GeradoraDeEntidades.geraCliente;
import static com.br.heau.testes.utils.GeradoraDeEntidades.geraClienteDTO;
import static com.br.heau.util.GeradoraDeMensagens.mensagemSucessoCadastroCliente;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class OperacoesComClientesTest implements ITestCasesClientes {

    @Autowired
    private ClienteController clienteController;

    @Mock
    IRepositorioClientes repositorioClientes;

    @InjectMocks
    ClienteService clienteService;

    @BeforeEach
    public void setaService(){
        this.clienteController= new ClienteController(clienteService);
    }

    @Test
    public void deveriaTerSucessoAoCadastrarCliente(){
        assertEquals(ResponseEntity.status(HttpStatus.CREATED)
                        .body(mensagemSucessoCadastroCliente(geraClienteDTO().getNome(), geraClienteDTO().getNumeroContaOpcional().toString())),
                clienteController.cadastraCliente(geraClienteDTO()));
    }

    @Test
    public void deveriaFalharAoCadastrarClienteComNomeInvalido()  {
        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Constantes.USUARIO_CADASTRO_USUARIO_INVALIDO)
                , clienteController.cadastraCliente(geraClienteDTO("3bruno", 2.0)));
    }

    @Test
    public void deveriaFalharAoCadastrarClienteComNomeVazio() {
        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Constantes.USUARIO_CADASTRO_USUARIO_INVALIDO)
                , clienteController.cadastraCliente(geraClienteDTO("",200.0)));
    }

    @Test
    public void deveriaFalharAoCadastrarClienteComSaldoMenorQueZero(){
        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Constantes.SALDO_INVALIDO)
                , clienteController.cadastraCliente(geraClienteDTO("Bruno", -9.0)));
    }

    @Test
    public void deveriaTerSucessoAoListarClientes() {

        List<Cliente>idsSalvos = new ArrayList<>();
        idsSalvos.add(geraCliente(1L));
        idsSalvos.add(geraCliente(2L));

        given(repositorioClientes.findAll()).willReturn(idsSalvos);

        ResponseEntity<List<Cliente>> e = clienteController.listaClientes();
        assertEquals(clienteController.listaClientes().getBody().size(), idsSalvos.size());
    }

    @Test
    public void deveriaRetornarListaVazia() {
        assertNull(clienteController.listaClientes().getBody());
    }

    @Test
    public void deveriaTerSucessoAoBuscarClientePorConta() {
        clienteController.cadastraCliente(geraClienteDTO("Joao", 2.0, 1L));
        assertNotNull(clienteController.buscaPelaConta(1L));
    }

    @Test
    public void deveriaNaoEncontrarClientePorConta() {
        assertFalse(clienteController.buscaPelaConta(1L).getBody().isPresent());
    }
}
