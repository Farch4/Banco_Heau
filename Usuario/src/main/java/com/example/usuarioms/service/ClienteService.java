package com.example.usuarioms.service;

import com.example.usuarioms.data.IRepositorioClientes;
import com.example.usuarioms.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ClienteService {

    private final IRepositorioClientes repositorio;

    @Autowired
    ClienteService(IRepositorioClientes repositorioClientes){
        repositorio = repositorioClientes;
    }


    public String cadastroCliente(Cliente cliente){
        if(repositorio.findByNumero(cliente.getNumero()).isPresent()) return "Número de conta submetido já consta nos registros.";
        repositorio.save(cliente);
        return "Usuário cadastrado com sucesso.";
    }

    public List<Cliente> ListaClientes() {
        return repositorio.findAll();
    }

    public Optional<Cliente> buscaPelaConta(Long numeroConta) {
        return repositorio.findByNumero(numeroConta);
    }
}
