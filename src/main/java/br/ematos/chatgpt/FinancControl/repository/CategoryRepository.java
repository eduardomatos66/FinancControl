package br.ematos.chatgpt.FinancControl.repository;

import br.ematos.chatgpt.FinancControl.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {


    List<Category> findByName(String name);

    @Query("SELECT c FROM Category c WHERE c.name LIKE %:keyword%")
    List<Category> findByKeyword(String keyword);
}