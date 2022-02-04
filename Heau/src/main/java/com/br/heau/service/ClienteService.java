package com.br.heau.service;
import com.br.heau.data.IRepositorioClientes;
import com.br.heau.model.Cliente;
import com.br.heau.model.dto.ClienteDTO;
import com.br.heau.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final IRepositorioClientes repositorio;

    @Autowired
    public ClienteService(IRepositorioClientes repositorioClientes){
        repositorio = repositorioClientes;
    }


    public String cadastroCliente(ClienteDTO clienteDTO){
        try {
            Cliente cliente = new Cliente(clienteDTO);
            repositorio.save(cliente);
            return cliente.getNome().concat(Constantes.USUARIO_CADASTRO_SUCESSO)
                    .concat(cliente.getConta().getId().toString());
        }catch (Exception e){
            return Constantes.USUARIO_CADASTRO_FALHA_GENERICA;
        }
    }

    public List<Cliente> ListaClientes() {
        return repositorio.findAll();
    }

    public Optional<Cliente> buscaPelaConta(Long numeroConta) {
        return repositorio.findByContaId(numeroConta);
    }
}
