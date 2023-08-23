package br.ematos.chatgpt.FinancControl.service;

import br.ematos.chatgpt.FinancControl.entity.IncomeEntity;
import br.ematos.chatgpt.FinancControl.repository.IncomeRepository;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class IncomeService extends AbstractService<IncomeEntity> {

    private final IncomeRepository incomeRepository;

    @Override
    public Optional<IncomeEntity> findById(Integer id) {
        return Optional.of(incomeRepository.getReferenceById(id));
    }

    public List<IncomeEntity> findByDate(Date date) {
        return incomeRepository.findByDate(date);
    }

    public List<IncomeEntity> findByFromWho(String fromWho) {
        return incomeRepository.findByFromWho(fromWho);
    }

    public List<IncomeEntity> findByComment(String comment) {
        return incomeRepository.findByComment(comment);
    }

    public List<IncomeEntity> findByValue(Long value) {
        return incomeRepository.findByValue(value);
    }

    public List<IncomeEntity> findByIsRealized(Boolean isRealized) {
        return incomeRepository.findByIsRealized(isRealized);
    }

    public IncomeEntity createIncome(IncomeEntity income) {
        return incomeRepository.save(income);
    }

    public IncomeEntity updateIncome(Integer id, IncomeEntity updatedIncome) {
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
        incomeRepository.deleteById(id);
        return true;
    }
}