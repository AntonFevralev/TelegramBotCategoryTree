package com.github.AntonFevralev.TelegramBotCategoryTree.bot;

import com.github.AntonFevralev.TelegramBotCategoryTree.bot.config.TelegramBotConfig;
import com.github.AntonFevralev.TelegramBotCategoryTree.command.CommandContainer;
import com.github.AntonFevralev.TelegramBotCategoryTree.service.impl.CategoryService;
import com.github.AntonFevralev.TelegramBotCategoryTree.service.impl.ExcelService;
import com.github.AntonFevralev.TelegramBotCategoryTree.service.impl.SendBotMessageService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.AntonFevralev.TelegramBotCategoryTree.command.CommandName.NO;

/**
 * Бот
 */
@Component
public class TelegramBot extends TelegramLongPollingBot {

    public static String COMMAND_PREFIX = "/";

    private final TelegramBotConfig botConfig;

    private final CommandContainer commandContainer;

    private final CategoryService categoryService;

    private final ExcelService excelService;



    public TelegramBot(TelegramBotConfig botConfig, CategoryService categoryService, ExcelService excelService) {
        this.botConfig = botConfig;
        this.excelService = excelService;
        this.commandContainer = new CommandContainer(new SendBotMessageService(this), categoryService,
                excelService);
        this.categoryService = categoryService;
    }

    /**
     * Передает входящие обновления обработчику соответствующей команды
     * @param update
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0];
                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            }else {
                commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
            }}else {
            commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
        }
        }


    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }
}
