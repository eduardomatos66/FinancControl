package br.ematos.chatgpt.financcontrol.service;

import br.ematos.chatgpt.financcontrol.entity.IncomeEntity;
import br.ematos.chatgpt.financcontrol.exception.EntityNotFoundException;
import br.ematos.chatgpt.financcontrol.repository.IncomeRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class IncomeService extends AbstractService<IncomeEntity> {

  private final IncomeRepository incomeRepository;

  @Override
  public Optional<IncomeEntity> findById(Integer id) {
    log.info("Finding IncomeEntity by ID: " + id);
    return Optional.of(incomeRepository.getReferenceById(id));
  }

  public List<IncomeEntity> findByDate(Date date) {
    log.info("Finding IncomeEntities by Date: " + date);
    return incomeRepository.findByDate(date);
  }

  public List<IncomeEntity> findByFromWho(String fromWho) {
    log.info("Finding IncomeEntities by From Who: " + fromWho);
    return incomeRepository.findByFromWho(fromWho);
  }

  public List<IncomeEntity> findByComment(String comment) {
    log.info("Finding IncomeEntities by Comment: " + comment);
    return incomeRepository.findByComment(comment);
  }

  public List<IncomeEntity> findByValue(Long value) {
    log.info("Finding IncomeEntities by Value: " + value);
    return incomeRepository.findByValue(value);
  }

  public List<IncomeEntity> findByIsRealized(Boolean isRealized) {
    log.info("Finding IncomeEntities by Is Realized: " + isRealized);
    return incomeRepository.findByIsRealized(isRealized);
  }

  public IncomeEntity createIncome(IncomeEntity income) {
    log.info("Creating a new IncomeEntity: " + income);
    return incomeRepository.findById(income.getId()).stream()
        .findFirst()
        .orElseGet(() -> incomeRepository.save(income));
  }

  public List<IncomeEntity> createIncomes(List<IncomeEntity> incomes) {
    log.info("Creating multiple IncomeEntities");
    return incomes.stream().map(this::createIncome).toList();
  }

  public IncomeEntity updateIncome(Integer id, IncomeEntity updatedIncome) {
    log.info("Updating IncomeEntity with ID: " + id);
    Optional<IncomeEntity> optionalIncome = incomeRepository.findById(id);
    if (optionalIncome.isEmpty()) {
      throw new EntityNotFoundException("Income with id " + id + " not found");
    }

    IncomeEntity income = optionalIncome.get();
    income.setDate(updatedIncome.getDate());
    income.setFromWho(updatedIncome.getFromWho());
    income.setComment(updatedIncome.getComment());
    income.setValue(updatedIncome.getValue());
    income.setIsRealized(updatedIncome.getIsRealized());
    return incomeRepository.save(income);
  }

  public boolean deleteIncome(Integer id) {
    log.info("Deleting IncomeEntity with ID: " + id);
    return incomeRepository
        .findById(id)
        .map(
            income -> {
              incomeRepository.delete(income);
              log.info("Income deleted successfully: " + income);
              return true;
            })
        .orElse(false);
  }
}
