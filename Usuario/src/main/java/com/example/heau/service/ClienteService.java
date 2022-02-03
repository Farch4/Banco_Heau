package com.example.heau.service;
import com.example.heau.data.IRepositorioClientes;
import com.example.heau.model.Cliente;
import com.example.heau.model.Conta;
import com.example.heau.model.dto.ClienteDTO;
import com.example.heau.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
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
        return repositorio.findByConta(new Conta(numeroConta, null));
    }
}
