package org.example.tgbotdocker.config;

import org.example.tgbotdocker.bot.Bot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@EnableScheduling
public class SpringConfig {


    @Bean
    public TelegramBotsApi telegramBotsApi(Bot bot) {
        try {
            TelegramBotsApi telegramBotsApi = new  TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
            return telegramBotsApi;

        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
