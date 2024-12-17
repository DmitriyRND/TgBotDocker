package org.example.tgbotdocker;

import org.example.tgbotdocker.model.pars.NewsDTO;
import org.example.tgbotdocker.service.JsoupService;
import org.junit.jupiter.api.Test;

import java.util.List;


class JsoupServiceTest {
  private JsoupService jsoupService = new JsoupService();

    @Test
    void getNewsHabr() {
       List<NewsDTO> newsDTOS =  jsoupService.getNewsHabr();

        for (NewsDTO newsDTO1 : newsDTOS) {
            System.out.println(newsDTO1.getText());
            System.out.println(newsDTO1.getHref());
            System.out.println();
        }
    }
}