package com.github.AntonFevralev.TelegramBotCategoryTree.command;

import com.github.AntonFevralev.TelegramBotCategoryTree.service.impl.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Обработчик неизвестной команды
 */
public class UnknownCommand implements Command {

    public final String UNKNOWN_MESSAGE = "Такой команды нет, напишите /help чтобы узнать доступные команды.";

    private final SendBotMessageService sendBotMessageService;

    public UnknownCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    /**
     * Сообщает клиенту о том, что такой команды нет
     * @param update
     */
    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), UNKNOWN_MESSAGE);
    }
}