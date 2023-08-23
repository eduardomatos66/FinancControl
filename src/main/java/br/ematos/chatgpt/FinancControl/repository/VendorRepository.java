package br.ematos.chatgpt.FinancControl.repository;

import br.ematos.chatgpt.FinancControl.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer> {

    List<Vendor> findByName(String name);

    List<Vendor> findByLogoNotNull(); // Find vendors with a non-null logo

    List<Vendor> findByLogoIsNull(); // Find vendors without a logo
}