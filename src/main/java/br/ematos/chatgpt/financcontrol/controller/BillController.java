package br.ematos.chatgpt.financcontrol.controller;

import br.ematos.chatgpt.financcontrol.entity.Bill;
import br.ematos.chatgpt.financcontrol.service.BillService;
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
@RequestMapping("/bills")
public class BillController {

  private final BillService billService;

  @GetMapping
  public List<Bill> getAllBills() {
    return billService.findAllBills();
  }

  @GetMapping("/by-document-date/{documentDate}")
  public List<Bill> getBillsByDocumentDate(@PathVariable Date documentDate) {
    return billService.findBillsByDocumentDate(documentDate);
  }

  @GetMapping("/by-total-greater-than/{total}")
  public List<Bill> getBillsWithTotalGreaterThan(@PathVariable Float total) {
    return billService.findBillsByTotal(total);
  }

  @GetMapping("/by-vendor/{vendorId}")
  public List<Bill> getBillsByVendor(@PathVariable Integer vendorId) {
    return billService.findBillsByVendor(vendorId);
  }

  @GetMapping("/with-picture")
  public List<Bill> getBillsWithPicture() {
    return billService.findBillsWithPicture();
  }

  @GetMapping("/without-picture")
  public List<Bill> getBillsWithoutPicture() {
    return billService.findBillsWithoutPicture();
  }

  @GetMapping("/by-total/{total}")
  public List<Bill> getBillsByTotal(@PathVariable Float total) {
    return billService.findBillsByTotal(total);
  }

  @GetMapping("/by-tax/{tax}")
  public List<Bill> getBillsByTax(@PathVariable Float tax) {
    return billService.findBillsByTax(tax);
  }

  @GetMapping("/by-created-date/{createdDate}")
  public List<Bill> getBillsByCreatedDate(@PathVariable Date createdDate) {
    return billService.findBillsByCreatedDate(createdDate);
  }

  @GetMapping("/by-tag/{tagId}")
  public ResponseEntity<List<Bill>> getBillsByTag(Integer tagId) {
    return ResponseEntity.ok(billService.findBillsByTag(tagId));
  }

  @GetMapping("/by-item/{itemId}")
  public ResponseEntity<List<Bill>> getBillsByItem(Integer itemId) {
    return ResponseEntity.ok(billService.findBillsByItem(itemId));
  }

  @PostMapping
  public ResponseEntity<Bill> createBill(@RequestBody Bill bill) {
    Bill createdBill = billService.createBill(bill);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdBill);
  }

  @PostMapping("/create-multiple")
  public ResponseEntity<List<Bill>> createBills(@RequestBody List<Bill> bills) {
    List<Bill> createdBills = billService.createBills(bills);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdBills);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBill(@PathVariable Integer id) {
    if (billService.deleteBill(id)) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Bill> updateBill(@PathVariable Integer id, @RequestBody Bill bill) {

    Optional<Bill> existingBill = billService.findById(id);

    if (existingBill.isPresent()) {
      Bill updatedBill = billService.updateBill(id, bill);
      return ResponseEntity.ok(updatedBill);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
