package br.ematos.chatgpt.financcontrol.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "internal_item_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternalItemInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "originalPrice")
  private double originalPrice;

  @Column(name = "gWeight")
  private double gWeight;

  @Column(name = "lbWeight")
  private double lbWeight;

  @Column(name = "ozWeight")
  private double ozWeight;

  @Column(name = "lWeight")
  private double lWeight;

  @Column(name = "feet")
  private double feet;

  @Column(name = "meter")
  private double meter;

  @Column(name = "unities")
  private int unities;
}
