package br.ematos.chatgpt.financcontrol.repository;

import br.ematos.chatgpt.financcontrol.entity.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

  List<Category> findByName(String name);

  @Query("SELECT c FROM Category c WHERE c.name LIKE %:keyword%")
  List<Category> findByKeyword(String keyword);
}
