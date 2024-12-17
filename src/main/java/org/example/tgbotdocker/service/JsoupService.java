package org.example.tgbotdocker.service;


import org.example.tgbotdocker.model.pars.NewsDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JsoupService {

    private String urlHabrNews = "https://habr.com/ru/news/";

    public List<NewsDTO> getNewsHabr() {
        List<NewsDTO> newsDTOList = new ArrayList<>();

        try {
            Document document = Jsoup.connect(urlHabrNews).get();
            Elements selects = document.select("article.tm-articles-list__item h2 a");


            for (Element element : selects) {
                String href = element.absUrl("href");
                String text = element.text();

                NewsDTO newsDTO = new NewsDTO(href, text);
                newsDTOList.add(newsDTO);
            }
            return newsDTOList;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
