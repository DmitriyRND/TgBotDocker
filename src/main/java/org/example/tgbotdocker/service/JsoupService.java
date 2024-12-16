package org.example.tgbotdocker;


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

    public List<News> getNewsHabr() {
        List<News> newsList = new ArrayList<>();

        try {
            Document document = Jsoup.connect(urlHabrNews).get();
            Elements selects = document.select("article.tm-articles-list__item h2 a");


            for (Element element : selects) {
                String href = element.absUrl("href");
                String text = element.text();

                News news = new News(href, text);
                newsList.add(news);
            }
            return newsList;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
