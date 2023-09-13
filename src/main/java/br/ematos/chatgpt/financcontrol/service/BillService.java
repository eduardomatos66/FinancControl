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
    log.info("Finding Bill by ID: " + id);
    return Optional.of(billRepository.getReferenceById(Long.valueOf(id)));
  }

  public List<Bill> findAllBills() {
    log.info("Finding all Bills");
    return billRepository.findAll();
  }

  public List<Bill> findBillsByDocumentDate(Date documentDate) {
    log.info("Finding Bills by Document Date: " + documentDate);
    return billRepository.findByDocumentDate(documentDate);
  }

  public List<Bill> findBillsByVendor(Integer vendorId) {
    log.info("Finding Bills by Vendor ID: " + vendorId);
    Optional<Vendor> vendor = vendorService.findById(vendorId);

    return vendor.map(billRepository::findByVendor).orElse(null);
  }

  public List<Bill> findBillsByTag(Integer tagId) {
    log.info("Finding Bills by Tag ID: " + tagId);
    Optional<Tag> tag = tagService.findById(tagId);

    return tag.map(billRepository::findByTagsContaining).orElse(null);
  }

  public List<Bill> findBillsByItem(Integer itemId) {
    log.info("Finding Bills by Item ID: " + itemId);
    Optional<BillItem> billItem = billItemService.findById(itemId);

    return billItem.map(billRepository::findByItemsContaining).orElse(null);
  }

  public List<Bill> findBillsByTotal(Float total) {
    log.info("Finding Bills by Total: " + total);
    return billRepository.findByTotal(total);
  }

  public List<Bill> findBillsByTax(Float tax) {
    log.info("Finding Bills by Tax: " + tax);
    return billRepository.findByTax(tax);
  }

  public List<Bill> findBillsByCreatedDate(Date createdDate) {
    log.info("Finding Bills by Created Date: " + createdDate);
    return billRepository.findByCreatedDate(createdDate);
  }

  public List<Bill> findBillsWithPicture() {
    log.info("Finding Bills with Picture");
    return billRepository.findByBillPictureNotNull();
  }

  public List<Bill> findBillsWithoutPicture() {
    log.info("Finding Bills without Picture");
    return billRepository.findByBillPictureIsNull();
  }

  public Bill createBill(Bill bill) {
    log.info("Creating a new Bill: " + bill);

    Vendor vendor = null;
    if (bill.getVendor() != null) {
      vendor = vendorService.findOrCreateVendor(bill.getVendor());
    }

    List<Tag> tags = null;
    if (bill.getTags() != null) {
      tags = tagService.findOrCreateTagList(bill.getTags());
    }

    List<BillItem> billItems = null;
    if (bill.getItems() != null) {
      billItems = billItemService.findOrCreateBillItemList(bill.getItems());
    }

    bill.setTags(tags);
    bill.setItems(billItems);
    bill.setVendor(vendor);

    if (bill.getId() == null) {
      return billRepository.save(bill);
    } else {
      return billRepository.findById(bill.getId()).stream()
          .findFirst()
          .orElseGet(() -> billRepository.save(bill));
    }
  }

  public List<Bill> createBills(List<Bill> bills) {
    log.info("Creating multiple Bills");
    return bills.stream().map(this::createBill).toList();
  }

  public boolean deleteBill(Integer id) {
    log.info("Deleting Bill with ID: " + id);
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
    log.info("Updating Bill with ID: " + id);
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
