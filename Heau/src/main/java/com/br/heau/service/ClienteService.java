package com.br.heau.service;

import com.br.heau.data.IRepositorioClientes;
import com.br.heau.model.Cliente;
import com.br.heau.model.dto.ClienteDTO;
import com.br.heau.util.Constantes;
import com.br.heau.util.excecao.DominioException;
import com.br.heau.util.validator.ClienteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.br.heau.util.GeradoraDeMensagens.mensagemSucessoCadastroCliente;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final IRepositorioClientes repositorio;

    @Autowired
    public ClienteService(IRepositorioClientes repositorioClientes) {
        repositorio = repositorioClientes;
    }


    public String cadastroCliente(ClienteDTO clienteDTO) throws DominioException {

        ClienteValidator validador = new ClienteValidator(clienteDTO);
        try{
            validador.validate();
            Cliente cliente = new Cliente(clienteDTO);
            repositorio.save(cliente);
            return mensagemSucessoCadastroCliente(cliente.getNome(), cliente.getConta().getId().toString());
        }catch (DominioException e){
            throw e;
        }
    }

    public List<Cliente> ListaClientes() {
        return repositorio.findAll();
    }

    public Optional<Cliente> buscaPelaConta(Long numeroConta) {
        return repositorio.findByContaId(numeroConta);
    }
}
