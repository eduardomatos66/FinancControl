package br.ematos.chatgpt.financcontrol.service;

import br.ematos.chatgpt.financcontrol.entity.Bill;
import br.ematos.chatgpt.financcontrol.entity.BillItem;
import br.ematos.chatgpt.financcontrol.entity.Tag;
import br.ematos.chatgpt.financcontrol.entity.Vendor;
import br.ematos.chatgpt.financcontrol.repository.BillRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BillService extends AbstractService<Bill> {

  private final BillRepository billRepository;
  private final VendorService vendorService;
  private final TagService tagService;
  private final BillItemService billItemService;

  @Override
  public Optional<Bill> findById(Integer id) {
    return Optional.of(billRepository.getReferenceById(id));
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
    return billRepository.save(bill);
  }
}
