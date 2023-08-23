package br.ematos.chatgpt.FinancControl.spring;


import br.ematos.chatgpt.FinancControl.repository.BillItemRepository;
import br.ematos.chatgpt.FinancControl.repository.BillRepository;
import br.ematos.chatgpt.FinancControl.repository.CategoryRepository;
import br.ematos.chatgpt.FinancControl.repository.IncomeRepository;
import br.ematos.chatgpt.FinancControl.repository.TagRepository;
import br.ematos.chatgpt.FinancControl.repository.VendorRepository;
import br.ematos.chatgpt.FinancControl.service.BillItemService;
import br.ematos.chatgpt.FinancControl.service.BillService;
import br.ematos.chatgpt.FinancControl.service.CategoryService;
import br.ematos.chatgpt.FinancControl.service.IncomeService;
import br.ematos.chatgpt.FinancControl.service.TagService;
import br.ematos.chatgpt.FinancControl.service.VendorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public BillService billService(BillRepository billRepository,
                       VendorService vendorService,
                       TagService tagService,
                       BillItemService billItemService) {

        return new BillService(billRepository, vendorService, tagService, billItemService);
    }

    @Bean
    public BillItemService billItemService(BillItemRepository billItemRepository,
                                           CategoryService categoryService) {
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
