package com.github.AntonFevralev.TelegramBotCategoryTree.bot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Конфигурация бота
 */
@Configuration
@Data
@PropertySource("application.properties")
public class TelegramBotConfig {
    @Value("${bot.name}")
    String botName;
    @Value("${bot.token}")
    String token;
}