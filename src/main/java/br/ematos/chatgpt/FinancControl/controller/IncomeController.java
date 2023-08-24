package br.ematos.chatgpt.FinancControl.controller;

import br.ematos.chatgpt.FinancControl.entity.IncomeEntity;
import br.ematos.chatgpt.FinancControl.service.IncomeService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/income")
public class IncomeController {

  private final IncomeService incomeService;

  @GetMapping("/{id}")
  public ResponseEntity<IncomeEntity> findById(@PathVariable Integer id) {
    Optional<IncomeEntity> incomeEntity = incomeService.findById(id);
    return incomeEntity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/date/{date}")
  public ResponseEntity<List<IncomeEntity>> findByDate(@PathVariable Date date) {
    List<IncomeEntity> incomeEntities = incomeService.findByDate(date);
    return ResponseEntity.ok(incomeEntities);
  }

  @GetMapping("/fromWho/{fromWho}")
  public ResponseEntity<List<IncomeEntity>> findByFromWho(@PathVariable String fromWho) {
    List<IncomeEntity> incomeEntities = incomeService.findByFromWho(fromWho);
    return ResponseEntity.ok(incomeEntities);
  }

  @GetMapping("/comment/{comment}")
  public ResponseEntity<List<IncomeEntity>> findByComment(@PathVariable String comment) {
    List<IncomeEntity> incomeEntities = incomeService.findByComment(comment);
    return ResponseEntity.ok(incomeEntities);
  }

  @GetMapping("/value/{value}")
  public ResponseEntity<List<IncomeEntity>> findByValue(@PathVariable Long value) {
    List<IncomeEntity> incomeEntities = incomeService.findByValue(value);
    return ResponseEntity.ok(incomeEntities);
  }

  @GetMapping("/isRealized/{isRealized}")
  public ResponseEntity<List<IncomeEntity>> findByIsRealized(@PathVariable Boolean isRealized) {
    List<IncomeEntity> incomeEntities = incomeService.findByIsRealized(isRealized);
    return ResponseEntity.ok(incomeEntities);
  }

  @PostMapping
  public ResponseEntity<IncomeEntity> createIncome(@RequestBody IncomeEntity incomeEntity) {
    IncomeEntity createdIncome = incomeService.createIncome(incomeEntity);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdIncome);
  }

  @PutMapping("/{id}")
  public ResponseEntity<IncomeEntity> updateIncome(
      @PathVariable Integer id, @RequestBody IncomeEntity incomeEntity) {
    Optional<IncomeEntity> existingIncome = incomeService.findById(id);
    if (existingIncome.isPresent()) {
      IncomeEntity updatedIncome = incomeService.updateIncome(id, incomeEntity);
      return ResponseEntity.ok(updatedIncome);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteIncome(@PathVariable Integer id) {
    Optional<IncomeEntity> existingIncome = incomeService.findById(id);
    if (existingIncome.isPresent()) {
      incomeService.deleteIncome(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
