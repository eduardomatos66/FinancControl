package br.ematos.chatgpt.FinancControl.repository;

import br.ematos.chatgpt.FinancControl.entity.IncomeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class IncomeRepositoryTest {

    @Mock
    private IncomeRepository incomeRepository;

    private IncomeEntity income;

    @BeforeEach
    void setUp() {
        income = new IncomeEntity(1, new Date(), "John Doe", "Salary", 5000L, true);
    }

    @Test
    void findByDate() {
        List<IncomeEntity> expected = Arrays.asList(income);
        Mockito.when(incomeRepository.findByDate(Mockito.any())).thenReturn(expected);
        List<IncomeEntity> actual = incomeRepository.findByDate(new Date());
        assertEquals(expected, actual);
    }

    @Test
    void findByFromWho() {
        List<IncomeEntity> expected = Arrays.asList(income);
        Mockito.when(incomeRepository.findByFromWho(Mockito.any())).thenReturn(expected);
        List<IncomeEntity> actual = incomeRepository.findByFromWho("John Doe");
        assertEquals(expected, actual);
    }

    @Test
    void findByComment() {
        List<IncomeEntity> expected = Arrays.asList(income);
        Mockito.when(incomeRepository.findByComment(Mockito.any())).thenReturn(expected);
        List<IncomeEntity> actual = incomeRepository.findByComment("Salary");
        assertEquals(expected, actual);
    }

    @Test
    void findByValue() {
        List<IncomeEntity> expected = Arrays.asList(income);
        Mockito.when(incomeRepository.findByValue(Mockito.any())).thenReturn(expected);
        List<IncomeEntity> actual = incomeRepository.findByValue(5000L);
        assertEquals(expected, actual);
    }

    @Test
    void findByIsRealized() {
        List<IncomeEntity> expected = Arrays.asList(income);
        Mockito.when(incomeRepository.findByIsRealized(Mockito.any())).thenReturn(expected);
        List<IncomeEntity> actual = incomeRepository.findByIsRealized(true);
        assertEquals(expected, actual);
    }
}