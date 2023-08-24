package br.ematos.chatgpt.FinancControl.repository;

import br.ematos.chatgpt.FinancControl.entity.Category;
import br.ematos.chatgpt.FinancControl.entity.Tag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
  List<Tag> findByName(String name);

  @Query("SELECT t FROM Tag t WHERE t.name LIKE %:keyword%")
  List<Category> findByKeyword(String keyword);
}
