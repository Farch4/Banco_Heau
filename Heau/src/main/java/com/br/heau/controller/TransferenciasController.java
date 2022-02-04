package com.br.heau.controller;

import com.br.heau.model.dto.TransferenciaDTO;
import com.br.heau.service.ClienteService;
import com.br.heau.service.TransferenciasService;
import com.br.heau.model.Transferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("heau/v1/transferencias")
public class TransferenciasController {

    private final ClienteService clienteService;
    private final TransferenciasService transferenciasService;

    @Autowired
    public TransferenciasController(ClienteService clienteService, TransferenciasService transferenciasService, TransferenciasService transferenciasService1) {
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
        return ResponseEntity.ok(transferenciasService.realizaTransferencia(transferenciaDTO));

    }

    @RequestMapping(method = RequestMethod.GET, value = "/listarTransferencias")
    @ResponseBody
    public ResponseEntity<List<TransferenciaDTO>> listaTransferencias(@RequestParam Long numeroConta) {
        return ResponseEntity.ok(transferenciasService.listaTransferenciasPorConta(numeroConta));

    }

}
