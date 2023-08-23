package br.ematos.chatgpt.FinancControl.service;

import br.ematos.chatgpt.FinancControl.entity.IncomeEntity;
import br.ematos.chatgpt.FinancControl.repository.IncomeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class IncomeServiceTest {

  @Mock
  private IncomeRepository incomeRepository;

  @InjectMocks
  private IncomeService incomeService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testFindById() {
    // given
    Integer id = 1;
    IncomeEntity incomeEntity = new IncomeEntity();
    incomeEntity.setId(id);

    when(incomeRepository.findById(id)).thenReturn(Optional.of(incomeEntity));

    // when
    Optional<IncomeEntity> result = incomeService.findById(id);

    // then
    assertTrue(result.isPresent());
    assertEquals(id, result.get().getId());
  }

  @Test
  public void testFindByDate() {
    Date date = new Date();
    IncomeEntity income = new IncomeEntity(1, date, "John", "Salary", 1000L, true);
    when(incomeRepository.findByDate(new Date())).thenReturn(List.of(income));
    List<IncomeEntity> result = incomeService.findByDate(date);
    Assertions.assertEquals(1, result.size());
    Assertions.assertEquals(income, result.get(0));
  }

  @Test
  public void testFindByFromWho() {
    IncomeEntity income1 = new IncomeEntity(1, new Date(), "John", "Salary", 1000L, true);
    IncomeEntity income2 = new IncomeEntity(2, new Date(), "Mary", "Bonus", 500L, false);
    when(incomeRepository.findByFromWho("John")).thenReturn(List.of(income1));
    List<IncomeEntity> result = incomeService.findByFromWho("John");
    Assertions.assertEquals(1, result.size());
    Assertions.assertEquals(income1, result.get(0));
  }

  @Test
  public void testFindByComment() {
    IncomeEntity income1 = new IncomeEntity(1, new Date(), "John", "Salary", 1000L, true);
    IncomeEntity income2 = new IncomeEntity(2, new Date(), "Mary", "Bonus", 500L, false);
    when(incomeRepository.findByComment("Salary")).thenReturn(List.of(income1));
    List<IncomeEntity> result = incomeService.findByComment("Salary");
    Assertions.assertEquals(1, result.size());
    Assertions.assertEquals(income1, result.get(0));
  }

  @Test
  public void testFindByValue() {
    IncomeEntity income1 = new IncomeEntity(1, new Date(), "John", "Salary", 1000L, true);
    IncomeEntity income2 = new IncomeEntity(2, new Date(), "Mary", "Bonus", 500L, false);
    when(incomeRepository.findByValue(1000L)).thenReturn(List.of(income1));
    List<IncomeEntity> result = incomeService.findByValue(1000L);
    Assertions.assertEquals(1, result.size());
    Assertions.assertEquals(income1, result.get(0));
  }

  @Test
  void testFindByIsRealized() {
    IncomeEntity income1 = new IncomeEntity(1, new Date(), "John Doe", "Income 1", 1000L, true);
    IncomeEntity income2 = new IncomeEntity(2, new Date(), "Jane Doe", "Income 2", 2000L, false);
    IncomeEntity income3 = new IncomeEntity(3, new Date(), "Bob Smith", "Income 3", 3000L, true);

    when(incomeRepository.findByIsRealized(true)).thenReturn(List.of(income1, income3));
    when(incomeRepository.findByIsRealized(false)).thenReturn(List.of(income2));

    List<IncomeEntity> realizedIncomes = incomeService.findByIsRealized(true);
    assertEquals(2, realizedIncomes.size());
    assertTrue(realizedIncomes.contains(income1));
    assertTrue(realizedIncomes.contains(income3));

    List<IncomeEntity> unrealizedIncomes = incomeService.findByIsRealized(false);
    assertEquals(1, unrealizedIncomes.size());
    assertTrue(unrealizedIncomes.contains(income2));
  }

  @Test
  public void testUpdateIncome() {
    IncomeEntity income = new IncomeEntity(1, new Date(), "John Doe", "Monthly salary", 5000L, true);

    when(incomeRepository.findById(1)).thenReturn(Optional.of(income));
    when(incomeRepository.save(ArgumentMatchers.any(IncomeEntity.class))).thenReturn(income);

    IncomeEntity updatedIncome = new IncomeEntity(1, new Date(), "Jane Doe", "Updated salary", 5500L, false);

    incomeService.updateIncome(1, updatedIncome);

    Assertions.assertEquals("Jane Doe", income.getFromWho());
    Assertions.assertEquals("Updated salary", income.getComment());
    Assertions.assertEquals(5500L, income.getValue());
    Assertions.assertFalse(income.getIsRealized());
  }

  @Test
  public void testUpdateIncomeEntityNotFound() {
    when(incomeRepository.findById(1)).thenReturn(Optional.empty());

    IncomeEntity updatedIncome = new IncomeEntity(1, new Date(), "Jane Doe", "Updated salary", 5500L, false);

    Assertions.assertThrows(EntityNotFoundException.class, () -> {
      incomeService.updateIncome(1, updatedIncome);
    });
  }

  @Test
  public void testCreateIncome() {
    IncomeEntity incomeEntity = new IncomeEntity();
    incomeEntity.setId(1);
    incomeEntity.setDate(new Date());
    incomeEntity.setFromWho("John");
    incomeEntity.setComment("Salary");
    incomeEntity.setValue(1000L);
    incomeEntity.setIsRealized(true);

    when(incomeRepository.save(incomeEntity)).thenReturn(incomeEntity);

    IncomeEntity createdIncome = incomeService.createIncome(incomeEntity);

    assertNotNull(createdIncome);
    assertEquals(incomeEntity, createdIncome);
  }

  @Test
  public void testDeleteIncome() {
    IncomeEntity incomeEntity = new IncomeEntity();
    incomeEntity.setId(1);
    incomeEntity.setDate(new Date());
    incomeEntity.setFromWho("John");
    incomeEntity.setComment("Salary");
    incomeEntity.setValue(1000L);
    incomeEntity.setIsRealized(true);

    when(incomeRepository.findById(incomeEntity.getId())).thenReturn(Optional.of(incomeEntity));

    boolean deleted = incomeService.deleteIncome(incomeEntity.getId());
    assertTrue(deleted);
  }
}