package com.br.heau.util.validator;
import com.br.heau.model.dto.ClienteDTO;
import com.br.heau.util.Constantes;
import com.br.heau.util.exception.DominioException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClienteValidator {

    ClienteDTO clienteDTO;


    public Boolean validate() throws DominioException {
        return validaNome();
    }

    private Boolean validaNome() throws DominioException {
        if(clienteDTO.getNome().trim().length()==0 || clienteDTO.getNome().isEmpty() || clienteDTO.getNome().matches(".*\\d.*")){
            throw new DominioException(Constantes.USUARIO_CADASTRO_USUARIO_INVALIDO);
        }
        return validaSaldo();
    }

    private Boolean validaSaldo() throws DominioException {
        if(clienteDTO.getSaldo()<0){
            throw new DominioException(Constantes.SALDO_INVALIDO);
        }
        return true;
    }

}



