package br.ematos.chatgpt.financcontrol.service;

import br.ematos.chatgpt.financcontrol.entity.Vendor;
import br.ematos.chatgpt.financcontrol.exception.EntityNotFoundException;
import br.ematos.chatgpt.financcontrol.repository.VendorRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class VendorService extends AbstractService<Vendor> {

  private final VendorRepository vendorRepository;

  @Override
  public Optional<Vendor> findById(Integer id) {
    return Optional.of(vendorRepository.getReferenceById(id));
  }

  public List<Vendor> findAllVendors() {
    return vendorRepository.findAll();
  }

  public List<Vendor> findVendorsByName(String name) {
    return vendorRepository.findByName(name);
  }

  public List<Vendor> findVendorsWithLogo() {
    return vendorRepository.findByLogoNotNull();
  }

  public List<Vendor> findVendorsWithoutLogo() {
    return vendorRepository.findByLogoIsNull();
  }

  public Vendor createVendor(Vendor vendor) {
    return vendorRepository.findByName(vendor.getName()).stream()
        .findFirst()
        .orElseGet(() -> vendorRepository.save(vendor));
  }

  public boolean deleteVendor(Integer id) {
    return vendorRepository
        .findById(id)
        .map(
            vendor -> {
              vendorRepository.delete(vendor);
              log.info("Vendor deleted successfully: " + vendor);
              return true;
            })
        .orElse(false);
  }

  public Vendor updateVendor(Integer id, Vendor updatedVendor) {
    Optional<Vendor> optionalVendor = vendorRepository.findById(id);
    if (optionalVendor.isEmpty()) {
      throw new EntityNotFoundException("Vendor with id " + id + " not found");
    }

    Vendor vendor = optionalVendor.get();
    vendor.setLogo(updatedVendor.getLogo());
    vendor.setName(updatedVendor.getName());
    return vendorRepository.save(vendor);
  }
}
