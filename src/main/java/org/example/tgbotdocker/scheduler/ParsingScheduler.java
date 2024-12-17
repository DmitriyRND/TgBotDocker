package org.example.tgbotdocker.scheduler;


import org.example.tgbotdocker.model.entity.NewsEntity;
import org.example.tgbotdocker.model.pars.NewsDTO;
import org.example.tgbotdocker.service.JsoupService;
import org.example.tgbotdocker.service.NewsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ParsingScheduler {
    private NewsService newsService;
    private JsoupService jsoupService;

    public ParsingScheduler(NewsService newsService, JsoupService jsoupService) {
        this.newsService = newsService;
        this.jsoupService = jsoupService;
    }

    @Scheduled(cron = "30 * * * * *", zone = "Europe/Moscow")
    public void parsingHabr(){
        //удалить список новостей
        //отправлялись каждые 4 часа 1 новость, отдельная ветка
        newsService.deleteAllNewsList();
        List<NewsDTO> newsDTOList = jsoupService.getNewsHabr();
        List<NewsEntity> newsEntityList = newsService.mappingNewses(newsDTOList);
        newsService.saveAll(newsEntityList);

    }

}
