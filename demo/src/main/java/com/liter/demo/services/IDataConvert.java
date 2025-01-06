package com.liter.demo.services;

public interface IDataConvert{
    <T> T obterDados(String json, Class<T> classe);

    <T> T getDados(String json, Class<T> classe);
}

