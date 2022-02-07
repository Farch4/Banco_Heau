package com.br.heau.testes;

import com.br.heau.util.exception.DominioException;

public interface ITestCasesClientes {

     void deveriaTerSucessoAoCadastrarCliente() throws DominioException;
     void deveriaFalharAoCadastrarClienteComNomeInvalido() throws DominioException;
     void deveriaFalharAoCadastrarClienteComNomeVazio() throws DominioException;
     void deveriaFalharAoCadastrarClienteComSaldoMenorQueZero() throws DominioException;
     void deveriaTerSucessoAoListarClientes();
     void deveriaRetornarListaVazia();
     void deveriaTerSucessoAoBuscarClientePorConta();
     void deveriaNaoEncontrarClientePorConta();
    }
