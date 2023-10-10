package com.github.AntonFevralev.TelegramBotCategoryTree.command;

/**
 * Перечисление команд
 */
public enum CommandName {

    VIEW_TREE("/viewTree"),
    ADD_ELEMENT("/addElement"),
    REMOVE_ELEMENT("/removeElement"),
    HELP("/help"),
    START("/start"),
    DOWNLOAD("/download"),
    NO("/noSuchCommand");
    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }

}