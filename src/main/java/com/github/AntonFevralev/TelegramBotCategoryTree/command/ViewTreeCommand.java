package com.github.AntonFevralev.TelegramBotCategoryTree.command;

import com.github.AntonFevralev.TelegramBotCategoryTree.service.impl.CategoryService;
import com.github.AntonFevralev.TelegramBotCategoryTree.service.impl.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.NoSuchElementException;

/**
 * Обработчик команды /viewTree
 */
public class ViewTreeCommand implements Command {
        private final SendBotMessageService sendBotMessageService;

        private final CategoryService categoryService;

        public ViewTreeCommand(SendBotMessageService sendBotMessageService, CategoryService categoryService) {
            this.sendBotMessageService = sendBotMessageService;
            this.categoryService = categoryService;
        }

    /**
     * Отображает категории из БД в виде дерева категорий
     * @param update
     */
        @Override
        public void execute(Update update) {
            int parentId = categoryService.findMinParent();
            try{
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),
                    categoryService.createCategoryTree(new StringBuilder(),parentId,0));
            }
            catch (NoSuchElementException e){
                sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),
                        e.getMessage());
            }

    }
}
