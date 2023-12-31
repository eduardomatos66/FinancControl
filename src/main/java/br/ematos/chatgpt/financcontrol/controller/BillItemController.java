package br.ematos.chatgpt.financcontrol.controller;

import br.ematos.chatgpt.financcontrol.entity.BillItem;
import br.ematos.chatgpt.financcontrol.service.BillItemService;
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
@RequestMapping("/billitems")
public class BillItemController {

  private final BillItemService billItemService;

  @GetMapping
  public List<BillItem> getAllBillItems() {
    return billItemService.findAllBillItems();
  }

  @GetMapping("/by-category/{categoryId}")
  public List<BillItem> getBillItemsByCategory(@PathVariable Integer categoryId) {
    return billItemService.findBillItemsByCategory(categoryId);
  }

  @GetMapping("/by-description/{description}")
  public List<BillItem> getBillItemsByDescription(@PathVariable String description) {
    return billItemService.findBillItemsByDescription(description);
  }

  @GetMapping("/by-code/{code}")
  public List<BillItem> getBillItemsByCode(@PathVariable String code) {
    return billItemService.findBillItemsByCode(code);
  }

  @GetMapping("/bypricegreaterthan/{price}")
  public List<BillItem> getBillItemsWithPriceGreaterThan(@PathVariable Float price) {
    return billItemService.findBillItemsWithPriceGreaterThan(price);
  }

  @GetMapping("/by-qty/{qty}")
  public List<BillItem> getBillItemsByQty(@PathVariable Integer qty) {
    return billItemService.findBillItemsByQty(qty);
  }

  @GetMapping("/by-price/{price}")
  public List<BillItem> getBillItemsByPrice(@PathVariable Float price) {
    return billItemService.findBillItemsByPrice(price);
  }

  @GetMapping("/by-tax/{tax}")
  public List<BillItem> getBillItemsByTax(@PathVariable Float tax) {
    return billItemService.findBillItemsByTax(tax);
  }

  @GetMapping("/by-total/{total}")
  public List<BillItem> getBillItemsByTotal(@PathVariable Float total) {
    return billItemService.findBillItemsByTotal(total);
  }

  @PostMapping
  public ResponseEntity<BillItem> createBillItem(@RequestBody BillItem billItem) {
    BillItem createdBillItem = billItemService.findOrCreateBillItem(billItem);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdBillItem);
  }

  @PostMapping("/create-multiple")
  public ResponseEntity<List<BillItem>> createBillItems(@RequestBody List<BillItem> billItems) {
    List<BillItem> createdBillItems = billItemService.findOrCreateBillItemList(billItems);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdBillItems);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBillItem(@PathVariable Integer id) {
    if (billItemService.deleteBillItem(id)) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<BillItem> updateBill(
      @PathVariable Integer id, @RequestBody BillItem billItem) {

    Optional<BillItem> existingBillItem = billItemService.findById(id);

    if (existingBillItem.isPresent()) {
      BillItem updatedBillItem = billItemService.updateBillItem(id, billItem);
      return ResponseEntity.ok(updatedBillItem);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
