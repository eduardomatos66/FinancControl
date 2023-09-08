package br.ematos.chatgpt.financcontrol.repository;

import br.ematos.chatgpt.financcontrol.entity.Bill;
import br.ematos.chatgpt.financcontrol.entity.BillItem;
import br.ematos.chatgpt.financcontrol.entity.Tag;
import br.ematos.chatgpt.financcontrol.entity.Vendor;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
  List<Bill> findByDocumentDate(Date documentDate);

  List<Bill> findByVendor(Vendor vendor);

  List<Bill> findByTagsContaining(Tag tag);

  List<Bill> findByItemsContaining(BillItem item);

  List<Bill> findByTotal(Float total);

  List<Bill> findByTax(Float tax);

  List<Bill> findByCreatedDate(Date createdDate);

  List<Bill> findByBillPictureNotNull(); // Find bills with a non-null picture

  List<Bill> findByBillPictureIsNull(); // Find bills without a picture

  @Query("SELECT b FROM Bill b WHERE b.total > :amount")
  List<Bill> findByTotalGreaterThan(Float amount);
}
