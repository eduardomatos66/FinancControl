package br.ematos.chatgpt.financcontrol.controller;

import br.ematos.chatgpt.financcontrol.entity.Category;
import br.ematos.chatgpt.financcontrol.service.CategoryService;
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
@RequestMapping("/categories")
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping
  public List<Category> getAllCategories() {
    return categoryService.findAllCategories();
  }

  @GetMapping("/bycategoryname/{name}")
  public List<Category> getCategoriesByName(@PathVariable String name) {
    return categoryService.findCategoriesByName(name);
  }

  @GetMapping("/bykeyword/{keyword}")
  public List<Category> getCategoriesByKeyword(@PathVariable String keyword) {
    return categoryService.findCategoriesByKeyword(keyword);
  }

  @PostMapping
  public ResponseEntity<Category> createCategory(@RequestBody Category category) {
    Category createdCategory = categoryService.createCategory(category);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
  }
}
