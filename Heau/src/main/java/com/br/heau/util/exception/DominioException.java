package com.br.heau.util.exception;

public class DominioException extends Exception{
    private String mensagensDeErro;

    public DominioException(String...partes){
        mensagensDeErro="";
        for(String parte:partes){
            mensagensDeErro = mensagensDeErro.concat(parte);
        }
    }

    public String getMensagensDeErro() {
        return mensagensDeErro;
    }
}
