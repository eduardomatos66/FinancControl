package br.ematos.chatgpt.financcontrol.service;

import br.ematos.chatgpt.financcontrol.entity.Bill;
import br.ematos.chatgpt.financcontrol.entity.BillItem;
import br.ematos.chatgpt.financcontrol.entity.Tag;
import br.ematos.chatgpt.financcontrol.entity.Vendor;
import br.ematos.chatgpt.financcontrol.exception.EntityNotFoundException;
import br.ematos.chatgpt.financcontrol.repository.BillRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class BillService extends AbstractService<Bill> {

  private final BillRepository billRepository;
  private final VendorService vendorService;
  private final TagService tagService;
  private final BillItemService billItemService;

  @Override
  public Optional<Bill> findById(Integer id) {
    return Optional.of(billRepository.getReferenceById(Long.valueOf(id)));
  }

  public List<Bill> findAllBills() {
    return billRepository.findAll();
  }

  public List<Bill> findBillsByDocumentDate(Date documentDate) {
    return billRepository.findByDocumentDate(documentDate);
  }

  public List<Bill> findBillsByVendor(Integer vendorId) {
    Optional<Vendor> vendor = vendorService.findById(vendorId);

    return vendor.map(billRepository::findByVendor).orElse(null);
  }

  public List<Bill> findBillsByTag(Integer tagId) {
    Optional<Tag> tag = tagService.findById(tagId);

    return tag.map(billRepository::findByTagsContaining).orElse(null);
  }

  public List<Bill> findBillsByItem(Integer itemId) {
    Optional<BillItem> billItem = billItemService.findById(itemId);

    return billItem.map(billRepository::findByItemsContaining).orElse(null);
  }

  public List<Bill> findBillsByTotal(Float total) {
    return billRepository.findByTotal(total);
  }

  public List<Bill> findBillsByTax(Float tax) {
    return billRepository.findByTax(tax);
  }

  public List<Bill> findBillsByCreatedDate(Date createdDate) {
    return billRepository.findByCreatedDate(createdDate);
  }

  public List<Bill> findBillsWithPicture() {
    return billRepository.findByBillPictureNotNull();
  }

  public List<Bill> findBillsWithoutPicture() {
    return billRepository.findByBillPictureIsNull();
  }

  public Bill createBill(Bill bill) {
    return billRepository.findById(bill.getId()).stream()
        .findFirst()
        .orElseGet(() -> billRepository.save(bill));
  }

  public List<Bill> createBills(List<Bill> bills) {
    return bills.stream().map(this::createBill).toList();
  }

  public boolean deleteBill(Integer id) {
    return billRepository
        .findById(Long.valueOf(id))
        .map(
            bill -> {
              billRepository.delete(bill);
              log.info("Bill deleted successfully: " + bill);
              return true;
            })
        .orElse(false);
  }

  public Bill updateBill(Integer id, Bill updatedBill) {
    Optional<Bill> optionalBill = billRepository.findById(Long.valueOf(id));
    if (optionalBill.isEmpty()) {
      throw new EntityNotFoundException("Bill with id " + id + " not found");
    }

    Bill bill = optionalBill.get();
    bill.setBillPicture(updatedBill.getBillPicture());
    bill.setCreatedDate(updatedBill.getCreatedDate());
    bill.setDocumentDate(updatedBill.getDocumentDate());
    bill.setItems(updatedBill.getItems());
    bill.setTags(updatedBill.getTags());
    bill.setTax(updatedBill.getTax());
    bill.setTotal(updatedBill.getTotal());
    bill.setVendor(updatedBill.getVendor());
    return billRepository.save(bill);
  }
}
