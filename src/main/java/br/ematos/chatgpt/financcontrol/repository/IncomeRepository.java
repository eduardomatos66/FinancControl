package br.ematos.chatgpt.financcontrol.repository;

import br.ematos.chatgpt.financcontrol.entity.IncomeEntity;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends JpaRepository<IncomeEntity, Integer> {

  Optional<IncomeEntity> findById(@Param("id") Integer id);

  List<IncomeEntity> findByDate(Date date);

  List<IncomeEntity> findByFromWho(String fromWho);

  List<IncomeEntity> findByComment(String comment);

  List<IncomeEntity> findByValue(Long value);

  List<IncomeEntity> findByIsRealized(Boolean isRealized);

  @Modifying
  @Query(
      "UPDATE IncomeEntity i SET i.date = :date, i.fromWho = :fromWho, i.comment = :comment, i.value = :value, i.isRealized = :isRealized WHERE i.id = :id")
  void updateIncome(
      @Param("id") Integer id,
      @Param("date") Date date,
      @Param("fromWho") String fromWho,
      @Param("comment") String comment,
      @Param("value") Long value,
      @Param("isRealized") Boolean isRealized);
}
