package br.ematos.chatgpt.financcontrol.repository;

import br.ematos.chatgpt.financcontrol.entity.BillItem;
import br.ematos.chatgpt.financcontrol.entity.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BillItemRepository extends JpaRepository<BillItem, Integer> {

  List<BillItem> findByCategory(Category category);

  List<BillItem> findByDescription(String description);

  List<BillItem> findByCode(String code);

  List<BillItem> findByQty(Integer qty);

  List<BillItem> findByPrice(Float price);

  List<BillItem> findByTax(Float tax);

  List<BillItem> findByTotal(Float total);

  @Query("SELECT bi FROM BillItem bi WHERE bi.price > :price")
  List<BillItem> findByPriceGreaterThan(Float price);
}
