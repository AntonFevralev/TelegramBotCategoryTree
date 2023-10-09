package com.github.AntonFevralev.TelegramBotCategoryTree.command;

import com.github.AntonFevralev.TelegramBotCategoryTree.service.impl.CategoryService;
import com.github.AntonFevralev.TelegramBotCategoryTree.service.impl.ExcelService;
import com.github.AntonFevralev.TelegramBotCategoryTree.service.impl.SendBotMessageService;
import com.google.common.collect.ImmutableMap;

import static com.github.AntonFevralev.TelegramBotCategoryTree.command.CommandName.*;

/**
 * Класс-контейнер команд бота
 */
public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    private final CategoryService categoryService;

    private final ExcelService excelService;

    /**
     * Добавляет команды и их обработчки в контейнер
     * @param sendBotMessageService
     * @param categoryService
     * @param excelService
     */
    public CommandContainer(SendBotMessageService sendBotMessageService, CategoryService categoryService,
                            ExcelService excelService) {

        commandMap = ImmutableMap.<String,Command>builder()
                .put(ADD_ELEMENT.getCommandName(), new AddElementCommand(sendBotMessageService, categoryService))
                .put(REMOVE_ELEMENT.getCommandName(), new RemoveElementCommand(sendBotMessageService, categoryService))
                .put(VIEW_TREE.getCommandName(), new ViewTreeCommand(sendBotMessageService, categoryService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(START.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .put(DOWNLOAD.getCommandName(), new DownloadCommand(sendBotMessageService, excelService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
        this.categoryService = categoryService;
        this.excelService = excelService;
    }

    /**
     * Извлекает команду из контейнера по ее названию
     * @param commandIdentifier
     * @return
     */
    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }

}