package br.ematos.chatgpt.financcontrol.service;

import br.ematos.chatgpt.financcontrol.entity.BillItem;
import br.ematos.chatgpt.financcontrol.entity.Category;
import br.ematos.chatgpt.financcontrol.repository.BillItemRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BillItemService extends AbstractService<BillItem> {

  private final BillItemRepository billItemRepository;
  private final CategoryService categoryService;

  @Override
  public Optional<BillItem> findById(Integer id) {
    return Optional.of(billItemRepository.getReferenceById(id));
  }

  public List<BillItem> findAllBillItems() {
    return billItemRepository.findAll();
  }

  public List<BillItem> findBillItemsByCategory(Integer categoryId) {
    Optional<Category> category = categoryService.findById(categoryId);

    return category.map(billItemRepository::findByCategory).orElse(null);
  }

  public List<BillItem> findBillItemsByDescription(String description) {
    return billItemRepository.findByDescription(description);
  }

  public List<BillItem> findBillItemsByCode(String code) {
    return billItemRepository.findByCode(code);
  }

  public List<BillItem> findBillItemsByQty(Integer qty) {
    return billItemRepository.findByQty(qty);
  }

  public List<BillItem> findBillItemsByPrice(Float price) {
    return billItemRepository.findByPrice(price);
  }

  public List<BillItem> findBillItemsByTax(Float tax) {
    return billItemRepository.findByTax(tax);
  }

  public List<BillItem> findBillItemsByTotal(Float total) {
    return billItemRepository.findByTotal(total);
  }

  public List<BillItem> findBillItemsWithPriceGreaterThan(Float price) {
    return billItemRepository.findByPriceGreaterThan(price);
  }

  public BillItem createBillItem(BillItem billItem) {
    return billItemRepository.save(billItem);
  }
}