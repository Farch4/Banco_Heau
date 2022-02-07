package com.br.heau.controller;

import com.br.heau.model.Cliente;
import com.br.heau.service.ClienteService;
import com.br.heau.model.dto.ClienteDTO;
import com.br.heau.util.exception.DominioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService){
        this.clienteService=clienteService;
    }

    /**
     * Cadastra novo cliente
     * @param cliente
     * @return status code e mensagem
     */
    @RequestMapping(method = RequestMethod.POST, value = "/cadastro")
    @ResponseBody
    public ResponseEntity<String> cadastraCliente(@RequestBody ClienteDTO cliente) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(clienteService.cadastroCliente(cliente));
        }catch (DominioException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMensagensDeErro());
        }
    }

    /**
     * Obtem todos os clientes cadastrados
     * @return lista com todos os clientes
     */
    @RequestMapping(method = RequestMethod.GET, value = "/lista")
    public ResponseEntity<List<Cliente>> listaClientes(){
        List<Cliente> lista = clienteService.ListaClientes();
        if(lista.size()>0){
            return ResponseEntity.ok(lista);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    /**
     * retorna um cliente pelo seu número da conta
     * @param numeroConta
     * @return status code e cliente segundo a conta
     */
    @RequestMapping(method = RequestMethod.GET, value= "/buscaPelaConta")
    public ResponseEntity<Optional<Cliente>> buscaPelaConta(@RequestParam Long numeroConta){
        Optional<Cliente> cliente = clienteService.buscaPelaConta(numeroConta);
        if(cliente.isPresent()){
            return ResponseEntity.ok(cliente);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Optional.empty());
    }

}
