package br.ematos.chatgpt.FinancControl.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "income")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "date")
  private Date date;

  @Column(name = "from_who")
  private String fromWho;

  @Column(name = "comment")
  private String comment;

  @Column(name = "value")
  private Long value;

  @Column(name = "is_realized")
  private Boolean isRealized;
}
