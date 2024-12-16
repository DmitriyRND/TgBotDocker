package org.example.tgbotdocker;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {

    @Autowired
    private JsoupService jsoupService;

    @Value("${bot.name}")
    private String botName;

    public Bot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
       if (!update.hasMessage() && update.getMessage().hasText()) {
           return;
       }
       StringBuffer sb = new StringBuffer();
        List<News> newsHabr = jsoupService.getNewsHabr();
        if (update.getMessage().getText().equalsIgnoreCase("news")){

            for (News news : newsHabr) {
                sb.append(news.getText()).append("\n").append(news.getHref()).append("\n\n");
            }
       }


        Long chatId = update.getMessage().getChatId();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(sb.toString());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public String getBotUsername() {
        return botName;
    }


}
