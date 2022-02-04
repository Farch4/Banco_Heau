package com.example.heau.controller;

import com.example.heau.model.Cliente;
import com.example.heau.model.dto.ClienteDTO;
import com.example.heau.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> cadastraCliente(@RequestBody ClienteDTO cliente) {
        return ResponseEntity.ok(clienteService.cadastroCliente(cliente));
    }

    /**
     * Obtem todos os clientes cadastrados
     * @return lista com todos os clientes
     */
    @RequestMapping(method = RequestMethod.GET, value = "/lista")
    public ResponseEntity<List<Cliente>> listaClientes(){
        return ResponseEntity.ok(clienteService.ListaClientes());
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
