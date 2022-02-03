package com.example.heau.model.enums;


public enum ResultadoTransferenciaEnum{

    SUCESSO("Sucesso"),
    FALHA("Falha");

    private String resultado;

    ResultadoTransferenciaEnum(String resultado) {
        this.resultado= resultado;
    }

    public String getResultado() {
        return resultado;
    }
}