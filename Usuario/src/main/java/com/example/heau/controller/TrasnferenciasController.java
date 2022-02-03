package com.example.heau.controller;

import com.example.heau.model.Transferencia;
import com.example.heau.model.dto.TransferenciaDTO;
import com.example.heau.service.ClienteService;
import com.example.heau.service.TransferenciasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("heau/v1/transferencias")
public class TrasnferenciasController {

    private final ClienteService clienteService;
    private final TransferenciasService transferenciasService;

    @Autowired
    public TrasnferenciasController(ClienteService clienteService, TransferenciasService transferenciasService, TransferenciasService transferenciasService1) {
        this.clienteService = clienteService;
        this.transferenciasService = transferenciasService;
    }

    /**
     * Cadastra novo cliente
     * @param transferenciaDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/realizarTransferencia")
    @ResponseBody
    public ResponseEntity<Transferencia> realizarTransferencia(@RequestBody TransferenciaDTO transferenciaDTO) {

        ResponseEntity<Transferencia> e = ResponseEntity.ok(transferenciasService.realizaTransferencia(transferenciaDTO));
        return e;

    }

}
