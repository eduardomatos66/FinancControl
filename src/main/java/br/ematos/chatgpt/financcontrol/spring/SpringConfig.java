package br.ematos.chatgpt.financcontrol.spring;

import br.ematos.chatgpt.financcontrol.repository.BillItemRepository;
import br.ematos.chatgpt.financcontrol.repository.BillRepository;
import br.ematos.chatgpt.financcontrol.repository.CategoryRepository;
import br.ematos.chatgpt.financcontrol.repository.IncomeRepository;
import br.ematos.chatgpt.financcontrol.repository.TagRepository;
import br.ematos.chatgpt.financcontrol.repository.VendorRepository;
import br.ematos.chatgpt.financcontrol.service.BillItemService;
import br.ematos.chatgpt.financcontrol.service.BillService;
import br.ematos.chatgpt.financcontrol.service.CategoryService;
import br.ematos.chatgpt.financcontrol.service.IncomeService;
import br.ematos.chatgpt.financcontrol.service.TagService;
import br.ematos.chatgpt.financcontrol.service.VendorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

  @Bean
  public BillService billService(
      BillRepository billRepository,
      VendorService vendorService,
      TagService tagService,
      BillItemService billItemService) {

    return new BillService(billRepository, vendorService, tagService, billItemService);
  }

  @Bean
  public BillItemService billItemService(
      BillItemRepository billItemRepository, CategoryService categoryService) {
    return new BillItemService(billItemRepository, categoryService);
  }

  @Bean
  public CategoryService categoryService(CategoryRepository categoryRepository) {
    return new CategoryService(categoryRepository);
  }

  @Bean
  public IncomeService incomeService(IncomeRepository incomeRepository) {
    return new IncomeService(incomeRepository);
  }

  @Bean
  public TagService tagService(TagRepository tagRepository) {
    return new TagService(tagRepository);
  }

  @Bean
  public VendorService vendorService(VendorRepository vendorRepository) {
    return new VendorService(vendorRepository);
  }
}
