package com.br.heau.model.enums;


public enum ResultadoTransferenciaEnum{

    SUCESSO("Sucesso"),
    SALDO_INCUFICIENTE("Falha: Saldo insuficiente"),
    VALOR_MAIOR("Falha: Valor maior do que o permitido"),
    CONTA_ORIGEM_INEXISTENTE("Falha: conta origem inexistente"),
    CONTA_DESTINO_INEXISTENTE("Falha: conta destino inexistente"),
    CONTAS_INEXISTENTES("Nenhuma das duas contas informadas foi encontrada");

    private String resultado;

    ResultadoTransferenciaEnum(String resultado) {
        this.resultado= resultado;
    }

    public String getResultado() {
        return resultado;
    }
}