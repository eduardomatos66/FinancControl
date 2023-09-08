package br.ematos.chatgpt.financcontrol.service;

import java.util.Optional;

public abstract class AbstractService<T> {
  public abstract Optional<T> findById(Integer id);
}
