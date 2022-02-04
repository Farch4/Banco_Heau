package com.br.heau.util.validator;
import com.br.heau.model.dto.ClienteDTO;
import com.br.heau.util.Constantes;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClienteValidator {

    ClienteDTO clienteDTO;


    public String validate(){
        return validaNome();
    }

    private String validaNome(){
        if(clienteDTO.getNome().trim().length()==0 || clienteDTO.getNome().isEmpty()){
            return Constantes.USUARIO_CADASTRO_USUARIO_NOME_VAZIO;
        }
        try{
            Long.parseLong(clienteDTO.getNome());
            Double.parseDouble(clienteDTO.getNome());
            return Constantes.USUARIO_CADASTRO_USUARIO_INVALIDO;
        }catch (NumberFormatException n){
            return validaSaldo();
        }
    }

    private String validaSaldo() {
        if(clienteDTO.getSaldo()<0)return Constantes.SALDO_INVALIDO;
        return null;
    }

}



