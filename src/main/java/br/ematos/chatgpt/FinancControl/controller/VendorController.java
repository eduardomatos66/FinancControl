package br.ematos.chatgpt.FinancControl.controller;

import br.ematos.chatgpt.FinancControl.entity.Vendor;
import br.ematos.chatgpt.FinancControl.service.VendorService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
}
