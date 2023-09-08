package br.ematos.chatgpt.financcontrol.controller;

import br.ematos.chatgpt.financcontrol.entity.Vendor;
import br.ematos.chatgpt.financcontrol.service.VendorService;
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
@RequestMapping("/vendors")
public class VendorController {

  private final VendorService vendorService;

  @GetMapping
  public List<Vendor> getAllVendors() {
    return vendorService.findAllVendors();
  }

  @GetMapping("/by-name/{name}")
  public List<Vendor> getVendorsByName(@PathVariable String name) {
    return vendorService.findVendorsByName(name);
  }

  @GetMapping("/with-logo")
  public List<Vendor> getVendorsWithLogo() {
    return vendorService.findVendorsWithLogo();
  }

  @GetMapping("/without-logo")
  public List<Vendor> getVendorsWithoutLogo() {
    return vendorService.findVendorsWithoutLogo();
  }

  @PostMapping
  public ResponseEntity<Vendor> createVendor(@RequestBody Vendor vendor) {
    Vendor createdVendor = vendorService.createVendor(vendor);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdVendor);
  }

  @PostMapping("/create-multiple")
  public ResponseEntity<List<Vendor>> createVendors(@RequestBody List<Vendor> vendors) {
    List<Vendor> createdVendors = vendorService.createVendors(vendors);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdVendors);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteVendor(@PathVariable Integer id) {
    if (vendorService.deleteVendor(id)) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Vendor> updateCategory(
      @PathVariable Integer id, @RequestBody Vendor vendor) {

    Optional<Vendor> existingTag = vendorService.findById(id);

    if (existingTag.isPresent()) {
      Vendor updatedVendor = vendorService.updateVendor(id, vendor);
      return ResponseEntity.ok(updatedVendor);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
