package com.br.heau.controller;

import com.br.heau.model.dto.TransferenciaDTO;
import com.br.heau.service.TransferenciasService;
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

@RestController
@RequestMapping("transferencias")
public class TransferenciasController {

    private final TransferenciasService transferenciasService;

    @Autowired
    public TransferenciasController(TransferenciasService transferenciasService) {
        this.transferenciasService = transferenciasService;
    }

    /**
     * Cadastra novo cliente
     * @param transferenciaDTO
     * @return status code e resultado da transferencia
     */
    @RequestMapping(method = RequestMethod.POST, value = "/realizarTransferencia")
    @ResponseBody
    public ResponseEntity<String> realizarTransferencia(@RequestBody TransferenciaDTO transferenciaDTO) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(transferenciasService.realizaTransferencia(transferenciaDTO));
        }catch (DominioException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMensagensDeErro());
        }

    }

    /**
     *
     * @param numeroConta
     * @return status code e lista de transfeerncias por conta
     */

    @RequestMapping(method = RequestMethod.GET, value = "/listarTransferencias")
    @ResponseBody
    public ResponseEntity<List<TransferenciaDTO>> listaTransferencias(@RequestParam Long numeroConta) {
        List<TransferenciaDTO> lista = transferenciasService.listaTransferenciasPorConta(numeroConta);
        if(lista.size()>0){
            return ResponseEntity.ok(lista);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
