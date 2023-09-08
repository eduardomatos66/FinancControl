package br.ematos.chatgpt.financcontrol.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bill_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @Column(name = "description")
  private String description;

  @Column(name = "code")
  private String code;

  @Column(name = "qty")
  private Integer qty;

  @Column(name = "price")
  private Float price;

  @Column(name = "tax")
  private Float tax;

  @Column(name = "total")
  private float total;

  @ManyToMany
  @JoinColumn(name = "internal_item_info")
  private List<InternalItemInfo> internalItemInfoList;
}
