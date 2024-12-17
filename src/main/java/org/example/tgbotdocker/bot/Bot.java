package org.example.tgbotdocker.bot;


import org.example.tgbotdocker.model.entity.NewsEntity;
import org.example.tgbotdocker.service.JsoupService;
import org.example.tgbotdocker.model.pars.NewsDTO;
import org.example.tgbotdocker.service.NewsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {

    private JsoupService jsoupService;
    private NewsService newsService;

    @Value("${bot.name}")
    private String botName;

    public Bot(@Value("${bot.token}") String botToken, JsoupService jsoupService, NewsService newsService) {
        super(botToken);
        this.jsoupService = jsoupService;
        this.newsService = newsService;

        List<NewsDTO> newsDTOList = this.jsoupService.getNewsHabr();
        List<NewsEntity> newsEntityList = newsService.mappingNewses(newsDTOList);
        newsService.saveAll(newsEntityList);

    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() && update.getMessage().hasText()) {
            return;
        }

        Long chatId = update.getMessage().getChatId();
        String messageText = update.getMessage().getText();

        if (messageText.equalsIgnoreCase("news")) {
            List<NewsEntity> news = newsService.getAllNews();
            StringBuilder sb = new StringBuilder();

            for (NewsEntity newsEntity : news) {
                sb.append(newsEntity.getText())
                        .append("\n")
                        .append(newsEntity.getHref())
                        .append("\n\n");
            }

            String responseText = sb.toString();
            sendLongMessage(chatId, responseText);
        } else {
            sendTextMessage(chatId, "Команда не распознана.");
        }

    }

    private List<String> splitMessage(String message, int maxLength) {
        List<String> parts = new ArrayList<>();
        int length = message.length();
        for (int start = 0; start < length; start += maxLength) {
            parts.add(message.substring(start, Math.min(length, start + maxLength)));
        }
        return parts;
    }
    private void sendLongMessage(Long chatId, String text) {
        if (text.length() > 4096) {
            List<String> parts = splitMessage(text, 4096);
            for (String part : parts) {
                sendTextMessage(chatId, part);
            }
        } else {
            sendTextMessage(chatId, text);
        }
    }
    private void sendTextMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(text);

        try {
            execute(sendMessage); // Отправка сообщения через Telegram API
        } catch (TelegramApiException e) {
            throw new RuntimeException("Ошибка при отправке сообщения", e);
        }
    }


    @Override
    public String getBotUsername() {
        return botName;
    }


}
