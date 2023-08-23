package br.ematos.chatgpt.FinancControl.service;

import java.util.Optional;

public abstract class AbstractService<T> {
    public abstract Optional<T> findById(Integer id);
}
