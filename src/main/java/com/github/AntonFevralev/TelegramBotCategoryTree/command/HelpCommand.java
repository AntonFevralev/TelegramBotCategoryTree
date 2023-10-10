package com.github.AntonFevralev.TelegramBotCategoryTree.command;

import com.github.AntonFevralev.TelegramBotCategoryTree.service.impl.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Обработчик команд /help и /start
 */
public class HelpCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public final String HELP_MESSAGE = String.format("Дотупные команды\n\n"
                    + "%s - Добавить корневой элемент, передав <Название категории>, или добавить подкатегорию, " +
                    "передав <родительская категория> <подкатегория>\n\n"
                    + "%s - Посмотреть дерево категорий\n\n"
                    + "%s - Удалить категорию \n\n"
                    + "%s - получить помощь в работе со мной\n",
            CommandName.ADD_ELEMENT.getCommandName(), CommandName.VIEW_TREE.getCommandName(), CommandName.REMOVE_ELEMENT.
                    getCommandName(), CommandName.HELP.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    /**
     * Отправляет сообщение с инструкцией к боту
     *
     * @param update
     */
    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}
