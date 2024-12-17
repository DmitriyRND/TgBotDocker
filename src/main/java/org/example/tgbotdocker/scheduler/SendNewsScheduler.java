package org.example.tgbotdocker.scheduler;


import org.example.tgbotdocker.bot.Bot;
import org.example.tgbotdocker.model.entity.NewsEntity;
import org.example.tgbotdocker.service.NewsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
public class SendNewsScheduler {
    private NewsService newsService;
    private Bot bot;
    private int currentNewsIndex = 0;

    public SendNewsScheduler(NewsService newsService, Bot bot) {
        this.newsService = newsService;
        this.bot = bot;
    }

    @Scheduled(cron = "* * 4 * * *", zone = "Europe/Moscow")
    public void sendNews() {


        var allNews = newsService.getAllNews();
        if (allNews.isEmpty()) {
            System.out.println("Новостей в базе данных нет");
            return;
        }

        NewsEntity news = allNews.get(currentNewsIndex);
        currentNewsIndex++;
        if (currentNewsIndex >= allNews.size()) {
            currentNewsIndex = 0;
        }

        sendNewsMessage(news);
    }

    public void sendNewsMessage(NewsEntity news) {
        String message = String.format(
                "Новость: %s\nСсылка: %s",
                news.getText(),
                news.getHref()

        );

        String messageText = String.format("Новость: %s\nСсылка: %s", news.getText(), news.getHref());
        for (Long chatId : bot.getChatIds()) {
            bot.sendTextMessage(chatId, messageText);
        }


    }


}
