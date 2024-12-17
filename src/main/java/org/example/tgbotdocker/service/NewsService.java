package org.example.tgbotdocker.service;

import org.example.tgbotdocker.model.entity.NewsEntity;
import org.example.tgbotdocker.model.pars.NewsDTO;
import org.example.tgbotdocker.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NewsService {


    private NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<NewsEntity> saveAll(List<NewsEntity> newsList) {
        return newsRepository.saveAll(newsList);
    }

    public List<NewsEntity> getAllNews() {
        return newsRepository.findAll();
    }

    public NewsEntity getNewsById(Long id) {
        return newsRepository.findById(id).orElse(null);
    }


    public void deleteAllNewsList(){
        newsRepository.deleteAll();
    }


    public List<NewsEntity> mappingNewses(List<NewsDTO> news) {
      return   news.stream()
                .map(newsDTO -> {
                    NewsEntity newsEntity = new NewsEntity();
                    newsEntity.setHref(newsDTO.getHref());
                    newsEntity.setText(newsDTO.getText());
                    newsEntity.setCreate_at(LocalDate.now());
                    return newsEntity;
                })
                .toList();
    }


}
