package com.br.heau.servicos;

import com.br.heau.data.IRepositorioClientes;
import com.br.heau.service.ClienteService;
import com.br.heau.util.Constantes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClienteServiceTests {

    ClienteService clienteService;
    @Mock
    IRepositorioClientes repositorioClientes;

    @BeforeEach
    public void beforeEach() {
        this.clienteService= new ClienteService(repositorioClientes);
    }

    @Test
    public void TesteDeveriaSucessoCadastroUsuario(){

    }

    @Test
    public void TesteDeveFalharGenericamente(){
        Assertions.assertEquals(Constantes.USUARIO_CADASTRO_FALHA_GENERICA, clienteService.cadastroCliente(null));
    }


}
