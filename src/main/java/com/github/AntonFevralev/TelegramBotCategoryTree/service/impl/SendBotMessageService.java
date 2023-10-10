package com.github.AntonFevralev.TelegramBotCategoryTree.service.impl;

import com.github.AntonFevralev.TelegramBotCategoryTree.bot.TelegramBot;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

/**
 * Сервис отправки сообщения через телеграм-бота
 */
@Service
public class SendBotMessageService {

    private final TelegramBot telegramBot;

    public SendBotMessageService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    /**
     * отправляет текстовое сообщение пользователю
     *
     * @param chatId  чат id пользователя
     * @param message текст сообщения
     */
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);
        sendMessage.enableMarkdown(true);

        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            //todo add logging to the project.
            e.printStackTrace();
        }
    }

    /**
     * Отправляет документ пользователю
     *
     * @param chatId   чат id пользователя
     * @param filePath путь к файлу
     * @throws TelegramApiException
     */
    public void sendExcelFile(long chatId, String filePath) throws TelegramApiException {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(String.valueOf(chatId));
        sendDocument.setDocument(new InputFile(new File(filePath)));
        telegramBot.execute(sendDocument);
    }
}