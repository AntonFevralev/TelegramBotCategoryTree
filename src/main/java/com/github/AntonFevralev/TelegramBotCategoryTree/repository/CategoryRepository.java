package com.github.AntonFevralev.TelegramBotCategoryTree.repository;

import com.github.AntonFevralev.TelegramBotCategoryTree.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий категорий
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /**
     * Получить все категории-потомки
     *
     * @param parentId id родителя
     * @return
     */
    Optional<List<Category>> findAllByParentId(Integer parentId);

    /**
     * Поиск минимального id корневой категории
     *
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT MIN(parent_id) FROM categories")
    int findMinParent();

    /**
     * Поиск категории по имени
     *
     * @param categoryName
     * @return
     */
    Optional<Category> findByName(String categoryName);

}
