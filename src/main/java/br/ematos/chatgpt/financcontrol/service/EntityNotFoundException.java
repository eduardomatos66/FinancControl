package br.ematos.chatgpt.financcontrol.service;

public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(String message) {
    super(message);
  }
}
