package com.github.AntonFevralev.TelegramBotCategoryTree.command;

import com.github.AntonFevralev.TelegramBotCategoryTree.service.impl.CategoryService;
import com.github.AntonFevralev.TelegramBotCategoryTree.service.impl.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Обработчик команды /removeElement
 */
public class RemoveElementCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final CategoryService categoryService;

    public RemoveElementCommand(SendBotMessageService sendBotMessageService, CategoryService categoryService) {
        this.sendBotMessageService = sendBotMessageService;
        this.categoryService = categoryService;
    }

    /**
     * Удаляет элемент и всех его потомков
     *
     * @param update
     */
    @Override
    public void execute(Update update) {
        String message = update.getMessage().getText();
        Pattern pattern = Pattern.compile("<(.*?)>");
        Matcher matcher = pattern.matcher(message);
        List<String> categoriesFromMessage = new ArrayList<>();
        while (matcher.find()) {
            String match = matcher.group(1);
            categoriesFromMessage.add(match);
        }
        if (categoriesFromMessage.size() == 1) {
            if (categoryService.checkIfCategoryExist(categoriesFromMessage.get(0))) {
                try {
                    categoryService.deleteCategoryAndDescendants(categoriesFromMessage.get(0));
                } catch (NoSuchElementException e) {
                    sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),
                            e.getMessage());
                }
            }
        } else {
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),
                    "Необходимо ввести одну категорию в <> скобках");
        }
    }
}
