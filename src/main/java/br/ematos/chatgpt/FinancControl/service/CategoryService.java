package br.ematos.chatgpt.FinancControl.service;

import br.ematos.chatgpt.FinancControl.entity.Category;
import br.ematos.chatgpt.FinancControl.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class CategoryService extends AbstractService<Category> {

    private final CategoryRepository categoryRepository;

    @Override
    public Optional<Category> findById(Integer id) {
        return Optional.of(categoryRepository.getReferenceById(id));
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> findCategoriesByName(String name) {
        return categoryRepository.findByName(name);
    }

    public List<Category> findCategoriesByKeyword(String keyword) {
        return categoryRepository.findByKeyword(keyword);
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }
}
