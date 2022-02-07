package com.br.heau.util.validator;
import com.br.heau.model.dto.ClienteDTO;
import com.br.heau.util.Constantes;
import com.br.heau.util.exception.DominioException;
import lombok.Data;

@Data
public class ClienteValidator {

    private final ClienteDTO clienteDTO;
    private String mensagemDeErro;

    public ClienteValidator(ClienteDTO clienteDTO) {
        this.clienteDTO = clienteDTO;
        mensagemDeErro="";
    }

    public String validate() throws DominioException {
        return validaNome();
    }

    private String validaNome() throws DominioException {
        if(clienteDTO.getNome().trim().length()==0 || clienteDTO.getNome().isEmpty() || clienteDTO.getNome().matches(".*\\d.*")){
            setMensagemDeErro(mensagemDeErro.concat(Constantes.USUARIO_CADASTRO_USUARIO_INVALIDO));
        }
        return validaSaldo();
    }

    private String validaSaldo() throws DominioException {
        if(clienteDTO.getSaldo()<0){
            setMensagemDeErro(mensagemDeErro.concat(Constantes.SALDO_INVALIDO));
        }
        return getMensagemDeErro();
    }

}



