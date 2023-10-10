package com.github.AntonFevralev.TelegramBotCategoryTree.command;

import com.github.AntonFevralev.TelegramBotCategoryTree.service.impl.CategoryService;
import com.github.AntonFevralev.TelegramBotCategoryTree.service.impl.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Обработчик команды /addElement
 */
public class AddElementCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final CategoryService categoryService;

    public AddElementCommand(SendBotMessageService sendBotMessageService, CategoryService categoryService) {
        this.sendBotMessageService = sendBotMessageService;
        this.categoryService = categoryService;
    }

    /**
     * Добавляет элемент в базу данных
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
        switch (categoriesFromMessage.size()) {
            case 1 -> {
                if (!categoryService.checkIfCategoryExist(categoriesFromMessage.get(0))) {
                    categoryService.addRootElement(categoriesFromMessage.get(0));
                } else {
                    sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),
                            "Такая категория уже есть");
                }
            }
            case 2 -> {
                boolean parentIsExist = categoryService.checkIfCategoryExist(categoriesFromMessage.get(0));
                boolean childIsExist = categoryService.checkIfCategoryExist(categoriesFromMessage.get(1));
                if (parentIsExist && !childIsExist) {
                    categoryService.addChildElement(categoriesFromMessage.get(0), categoriesFromMessage.get(1));
                } else if (!parentIsExist) {
                    sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),
                            "Родительская категория не существует");
                } else {
                    sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),
                            "Такая категория уже есть");
                }
            }
            default -> sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),
                    "Необходимо ввести либо одну корневую категорию," +
                            " либо <родительская категория> <дочерняя категория>");
        }
    }
}
