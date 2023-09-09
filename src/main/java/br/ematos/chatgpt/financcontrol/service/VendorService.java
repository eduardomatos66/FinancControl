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
    log.info("Finding Vendor by ID: " + id);
    return Optional.of(vendorRepository.getReferenceById(id));
  }

  public List<Vendor> findAllVendors() {
    log.info("Finding all Vendors");
    return vendorRepository.findAll();
  }

  public List<Vendor> findVendorsByName(String name) {
    log.info("Finding Vendors by Name: " + name);
    return vendorRepository.findByName(name);
  }

  public List<Vendor> findVendorsWithLogo() {
    log.info("Finding Vendors with Logo");
    return vendorRepository.findByLogoNotNull();
  }

  public List<Vendor> findVendorsWithoutLogo() {
    log.info("Finding Vendors without Logo");
    return vendorRepository.findByLogoIsNull();
  }

  public Vendor createVendor(Vendor vendor) {
    log.info("Creating a new Vendor: " + vendor);
    return vendorRepository.findByName(vendor.getName()).stream()
        .findFirst()
        .orElseGet(() -> vendorRepository.save(vendor));
  }

  public List<Vendor> createVendors(List<Vendor> vendors) {
    log.info("Creating multiple Vendors");
    return vendors.stream().map(this::createVendor).toList();
  }

  public boolean deleteVendor(Integer id) {
    log.info("Deleting Vendor with ID: " + id);
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
    log.info("Updating Vendor with ID: " + id);
    Optional<Vendor> optionalVendor = vendorRepository.findById(id);
    if (optionalVendor.isEmpty()) {
      throw new EntityNotFoundException("Vendor with id " + id + " not found");
    }

    Vendor vendor = optionalVendor.get();
    vendor.setLogo(updatedVendor.getLogo());
    vendor.setName(updatedVendor.getName());
    return vendorRepository.save(vendor);
  }

  public Vendor findOrCreateVendor(Vendor vendor) {
    Optional<Vendor> result = null;

    if (vendor.getId() != null) {
      result = findById(vendor.getId().intValue());
    } else if (vendor.getName() != null) {
      result = findVendorsByName(vendor.getName()).stream().findFirst();
    }

    if (result.isEmpty()) {
      return createVendor(vendor);
    }
    return result.get();
  }
}
