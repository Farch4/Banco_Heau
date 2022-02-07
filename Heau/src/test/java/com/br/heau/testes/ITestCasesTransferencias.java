package com.br.heau.testes;

import com.br.heau.util.exception.DominioException;

public interface ITestCasesTransferencias {

    void deveriaSucessoAoRealizarTransferencia() throws DominioException;
    void deveriaDarErroDeValorMaiorQueMilAoRealizarTransferencia() throws DominioException;
    void deveriaDarErroDeContaOrigemInexistenteAoRealizarTransferencia() throws DominioException;
    void deveriaDarErroDeContaDestinoInexistenteAoRealizarTransferencia() throws DominioException;
    void deveriaDarErroDeContasInexistentesAoRealizarTransferencia() throws DominioException;
    void deveriaDarErroComValorMenorQueZero() throws DominioException;
    void deveriaDarErroDeSaldoInsuficiente() throws DominioException;
    void deveriaSucessoAoListarTransferenciasPorConta();
    void deveriaTrazerResultadoVazioAoAcessarTransferenciasPorConta();


}
