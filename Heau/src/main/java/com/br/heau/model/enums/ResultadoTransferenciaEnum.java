package com.br.heau.model.enums;


    public enum ResultadoTransferenciaEnum {

    SUCESSO("Sucesso"),
    FALHA("Falha");

    private final String resultado;

    ResultadoTransferenciaEnum(String resultado) {
        this.resultado= resultado;
    }

    public String getResultado() {
        return resultado;
    }
}