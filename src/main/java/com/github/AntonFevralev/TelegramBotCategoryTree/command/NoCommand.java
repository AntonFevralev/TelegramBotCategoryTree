package com.github.AntonFevralev.TelegramBotCategoryTree.command;


import com.github.AntonFevralev.TelegramBotCategoryTree.service.impl.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Обработчик любого сообщения, не являющегося командой
 */
public class NoCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public final String NO_MESSAGE = "Вы не ввели команду. Введите команду, начинающуюся со слеша(/).\n"
            + "Чтобы посмотреть список команд введите /help";

    public NoCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), NO_MESSAGE);
    }

}