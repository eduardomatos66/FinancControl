package br.ematos.chatgpt.FinancControl.service;

import br.ematos.chatgpt.FinancControl.entity.Vendor;
import br.ematos.chatgpt.FinancControl.repository.VendorRepository;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
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
        return vendorRepository.save(vendor);
    }
}
