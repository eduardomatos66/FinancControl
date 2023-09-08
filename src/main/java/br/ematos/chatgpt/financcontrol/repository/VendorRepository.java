package br.ematos.chatgpt.financcontrol.repository;

import br.ematos.chatgpt.financcontrol.entity.Vendor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer> {

  List<Vendor> findByName(String name);

  List<Vendor> findByLogoNotNull(); // Find vendors with a non-null logo

  List<Vendor> findByLogoIsNull(); // Find vendors without a logo
}
