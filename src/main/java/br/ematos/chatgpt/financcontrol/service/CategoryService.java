package br.ematos.chatgpt.financcontrol.service;

import br.ematos.chatgpt.financcontrol.entity.Category;
import br.ematos.chatgpt.financcontrol.exception.EntityNotFoundException;
import br.ematos.chatgpt.financcontrol.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
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
    return categoryRepository.findByName(category.getName()).stream()
        .findFirst()
        .orElseGet(() -> categoryRepository.save(category));
  }

  public boolean deleteCategory(Integer id) {
    return categoryRepository
        .findById(id)
        .map(
            category -> {
              categoryRepository.delete(category);
              log.info("Category deleted successfully: " + category);
              return true;
            })
        .orElse(false);
  }

  public Category updateCategory(Integer id, Category updatedCategory) {
    Optional<Category> optionalCategory = categoryRepository.findById(id);
    if (optionalCategory.isEmpty()) {
      throw new EntityNotFoundException("Category with id " + id + " not found");
    }

    Category category = optionalCategory.get();
    category.setName(updatedCategory.getName());
    return categoryRepository.save(category);
  }
}
