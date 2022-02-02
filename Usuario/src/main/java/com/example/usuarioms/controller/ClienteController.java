package com.example.usuarioms.controller;

import com.example.usuarioms.model.Cliente;
import com.example.usuarioms.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("heau/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    ClienteController(ClienteService clienteService){
        this.clienteService=clienteService;
    }

    /**
     * Cadastra novo cliente
     * @param cliente
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/cadastro")
    @ResponseBody
    public String cadastraCliente(@RequestBody Cliente cliente) {
        return clienteService.cadastroCliente(cliente);
    }

    /**
     * Obtem todos os clientes cadastrados
     * @return lista com todos os clientes
     */
    @RequestMapping(method = RequestMethod.GET, value = "/lista")
    public List<Cliente> listaClientes(){
        return clienteService.ListaClientes();
    }

    /**
     * retorna um cliente pelo seu número da conta
     * @param numeroConta
     * @return Cliente
     */
    @RequestMapping(method = RequestMethod.GET, value= "/buscaPelaConta")
    public Optional<Cliente> buscaPelaConta(@RequestParam Long numeroConta){
        return clienteService.buscaPelaConta(numeroConta);
    }

}
