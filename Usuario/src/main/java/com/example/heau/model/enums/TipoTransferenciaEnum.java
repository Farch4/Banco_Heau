package com.example.heau.model.enums;

public enum TipoTransferenciaEnum {

    RECEBIDA("Recebida"),
    ENVIADA("Enviada");

    private String tipo;

    TipoTransferenciaEnum(String tipo) {
        this.tipo= tipo;
    }

    public String getResultado() {
        return tipo;
    }


}
