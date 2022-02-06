package com.br.heau.util;

public class GeradoraDeMensagens {

    public static String mensagemSucessoTransferencia(String...values){
        return Constantes.TRANSFERENCIA_SUCESSO+Constantes.CLIENTE_ORIGEM+values[0]+
                Constantes.CLIENTE_DESTINO+values[1]+Constantes.VALOR_TRANSFERIDO+ values[2];
    }

    public static String mensagemSucessoCadastroCliente(String...values){
        return values[0]+Constantes.USUARIO_CADASTRO_SUCESSO+values[1];
    }
}
