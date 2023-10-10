package com.github.AntonFevralev.TelegramBotCategoryTree.service.impl;

import com.github.AntonFevralev.TelegramBotCategoryTree.model.Category;
import com.github.AntonFevralev.TelegramBotCategoryTree.repository.CategoryRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Сервис по работе с Excel
 */
@Service
public class ExcelService {

    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    public ExcelService(CategoryRepository categoryRepository, CategoryService categoryService) {
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    }

    /**
     * Создает файл Excel с деревом категорий
     *
     * @return путь к файлу
     */
    public String createFile() {
        List<Category> categories = categoryRepository.findAll();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Categories");
            int rowIndex = 0;
            List<Category> printedCategories = new ArrayList<>();
            for (Category category : categories) {
                rowIndex = createCategoryRow(sheet, category, 0, rowIndex, printedCategories);
            }
            String filePath = "Categories.xlsx";
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                return filePath;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Рекурсивно создает строку - категорию в Excel
     *
     * @param sheet             лист
     * @param category          категория
     * @param depth             глубина вложенности
     * @param rowIndex          индекс строки
     * @param printedCategories список уже внесенных категорий
     * @return индекс строки
     */
    private int createCategoryRow(Sheet sheet, Category category, int depth, int rowIndex, List<Category> printedCategories) {
        if (!printedCategories.contains(category)) {
            Row row = sheet.createRow(rowIndex++);
            Cell cell = row.createCell(depth);
            cell.setCellValue(category.getName());
            printedCategories.add(category);
            List<Category> subcategories = categoryRepository.findAllByParentId(category.getId()).orElseThrow();
            for (Category subcategory : subcategories) {
                rowIndex = createCategoryRow(sheet, subcategory, depth + 1, rowIndex, printedCategories);
            }
        }
        return rowIndex;
    }


}
