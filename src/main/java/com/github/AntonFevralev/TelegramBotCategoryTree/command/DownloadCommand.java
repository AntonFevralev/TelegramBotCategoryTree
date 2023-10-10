package com.github.AntonFevralev.TelegramBotCategoryTree.command;

import com.github.AntonFevralev.TelegramBotCategoryTree.service.impl.ExcelService;
import com.github.AntonFevralev.TelegramBotCategoryTree.service.impl.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileNotFoundException;

/**
 * Обработчик команды /download
 */
public class DownloadCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final ExcelService excelService;


    public DownloadCommand(SendBotMessageService sendBotMessageService, ExcelService excelService) {
        this.sendBotMessageService = sendBotMessageService;
        this.excelService = excelService;
    }

    /**
     * Отправляет клиенту Excel файл с деревом категорий
     *
     * @param update
     */
    @Override
    public void execute(Update update) {
        try {
            sendBotMessageService.sendExcelFile(update.getMessage().getChatId(), excelService.createFile());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
