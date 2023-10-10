package com.github.AntonFevralev.TelegramBotCategoryTree.service.impl;

import com.github.AntonFevralev.TelegramBotCategoryTree.model.Category;
import com.github.AntonFevralev.TelegramBotCategoryTree.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Сервис для работы с категориями
 */
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Добавляет корневой элемент
     *
     * @param name имя элемента
     */
    public void addRootElement(String name) {
        Category category = new Category(0, name);
        categoryRepository.save(category);
    }

    /**
     * Добавляет элемент у которого есть родитель
     *
     * @param parent имя родителя
     * @param child  имя элемента
     */
    public void addChildElement(String parent, String child) {
        Integer parentId = categoryRepository.findByName(parent).orElseThrow().getId();
        Category category = new Category(parentId, child);
        categoryRepository.save(category);
    }

    /**
     * Создает строку - дерево категорий для вывода в telegram
     *
     * @param stringBuilder базовая строка
     * @param parentId      id родителя
     * @param depth         начальная глубина вложения элемента
     */
    public String createCategoryTree(StringBuilder stringBuilder, int parentId, int depth) {
        List<Category> categoryList = categoryRepository.findAllByParentId(parentId).
                orElseThrow(() -> new NoSuchElementException("Элементы не найдены"));
        Iterator<Category> iterator = categoryList.iterator();
        while (iterator.hasNext()) {
            Category cat = iterator.next();
            int categoryId = cat.getId();
            String categoryName = cat.getName();

            for (int i = 0; i < depth; i++) {
                stringBuilder.append("  ");
            }
            stringBuilder.append(categoryName).append("\n");
            createCategoryTree(stringBuilder, categoryId, depth + 1);
        }
        return stringBuilder.toString();
    }

    /**
     * Вызывыает метод удаления категории и всех ее потомков, если такая категория существует
     *
     * @param categoryName
     */
    @Transactional
    public void deleteCategoryAndDescendants(String categoryName) {
        Category categoryToDelete = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new NoSuchElementException("Категория не найдена"));
        deleteCategoryAndDescendantsRecursive(categoryToDelete);
    }

    /**
     * Удаляет рекурсивно категорию и ее потомков
     *
     * @param category
     */
    private void deleteCategoryAndDescendantsRecursive(Category category) {

        List<Category> children = categoryRepository.findAllByParentId(category.getId()).orElseThrow();
        for (Category child : children
        ) {
            deleteCategoryAndDescendantsRecursive(child);
        }
        categoryRepository.delete(category);
    }

    /**
     * @return id корневой категории
     */
    public int findMinParent() {
        return categoryRepository.findMinParent();
    }

    /**
     * Проверяет есть ли такая категории в БД
     *
     * @param s имя категории
     * @return
     */
    public boolean checkIfCategoryExist(String s) {
        return categoryRepository.findByName(s).isPresent();
    }

}
