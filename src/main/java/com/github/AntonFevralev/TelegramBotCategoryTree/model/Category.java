package com.github.AntonFevralev.TelegramBotCategoryTree.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность Категория
 */
@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "parent_id")
    Integer parentId;
    String name;

    public Category(Integer parentId, String name) {
        this.parentId = parentId;
        this.name = name;
    }
}
