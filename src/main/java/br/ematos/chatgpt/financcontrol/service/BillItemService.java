package br.ematos.chatgpt.financcontrol.service;

import br.ematos.chatgpt.financcontrol.entity.BillItem;
import br.ematos.chatgpt.financcontrol.entity.Category;
import br.ematos.chatgpt.financcontrol.exception.EntityNotFoundException;
import br.ematos.chatgpt.financcontrol.repository.BillItemRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class BillItemService extends AbstractService<BillItem> {

  private final BillItemRepository billItemRepository;
  private final CategoryService categoryService;

  @Override
  public Optional<BillItem> findById(Integer id) {
    log.info("Finding BillItem by ID: " + id);
    return Optional.of(billItemRepository.getReferenceById(id));
  }

  public List<BillItem> findAllBillItems() {
    log.info("Finding all BillItems");
    return billItemRepository.findAll();
  }

  public List<BillItem> findBillItemsByCategory(Integer categoryId) {
    log.info("Finding BillItems by Category ID: " + categoryId);
    Optional<Category> category = categoryService.findById(categoryId);

    return category.map(billItemRepository::findByCategory).orElse(null);
  }

  public List<BillItem> findBillItemsByDescription(String description) {
    log.info("Finding BillItems by Description: " + description);
    return billItemRepository.findByDescription(description);
  }

  public List<BillItem> findBillItemsByCode(String code) {
    log.info("Finding BillItems by Code: " + code);
    return billItemRepository.findByCode(code);
  }

  public List<BillItem> findBillItemsByQty(Integer qty) {
    log.info("Finding BillItems by Quantity: " + qty);
    return billItemRepository.findByQty(qty);
  }

  public List<BillItem> findBillItemsByPrice(Float price) {
    log.info("Finding BillItems by Price: " + price);
    return billItemRepository.findByPrice(price);
  }

  public List<BillItem> findBillItemsByTax(Float tax) {
    log.info("Finding BillItems by Tax: " + tax);
    return billItemRepository.findByTax(tax);
  }

  public List<BillItem> findBillItemsByTotal(Float total) {
    log.info("Finding BillItems by Total: " + total);
    return billItemRepository.findByTotal(total);
  }

  public List<BillItem> findBillItemsWithPriceGreaterThan(Float price) {
    log.info("Finding BillItems with Price Greater Than: " + price);
    return billItemRepository.findByPriceGreaterThan(price);
  }

  public BillItem findOrCreateBillItem(BillItem billItem) {
    log.info("Searching or creating a new BillItem: " + billItem);
    return billItemRepository.findById(billItem.getId()).stream()
        .findFirst()
        .orElseGet(() -> billItemRepository.save(billItem));
  }

  public List<BillItem> findOrCreateBillItemList(List<BillItem> billItems) {
    log.info("Searching or creating multiple BillItems");
    return billItems.stream().map(this::findOrCreateBillItem).toList();
  }

  public boolean deleteBillItem(Integer id) {
    log.info("Deleting BillItem with ID: " + id);
    return billItemRepository
        .findById(id)
        .map(
            billItem -> {
              billItemRepository.delete(billItem);
              log.info("BillItem deleted successfully: " + billItem);
              return true;
            })
        .orElse(false);
  }

  public BillItem updateBillItem(Integer id, BillItem updatedBillItem) {
    log.info("Updating BillItem with ID: " + id);
    Optional<BillItem> optionalBillItem = billItemRepository.findById(id);
    if (optionalBillItem.isEmpty()) {
      throw new EntityNotFoundException("BillItem with id " + id + " not found");
    }

    BillItem billItem = optionalBillItem.get();
    billItem.setCategory(updatedBillItem.getCategory());
    billItem.setCode(updatedBillItem.getCode());
    billItem.setDescription(updatedBillItem.getDescription());
    billItem.setInternalItemInfoList(updatedBillItem.getInternalItemInfoList());
    billItem.setPrice(updatedBillItem.getPrice());
    billItem.setQty(updatedBillItem.getQty());
    billItem.setTax(updatedBillItem.getTax());
    billItem.setTotal(updatedBillItem.getTotal());
    return billItemRepository.save(billItem);
  }
}
