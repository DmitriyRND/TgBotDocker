package org.example.tgbotdocker.controllers;

import org.example.tgbotdocker.model.entity.NewsEntity;
import org.example.tgbotdocker.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

   private final NewsService newsService;

    public AdminController(NewsService newsService) {
        this.newsService = newsService;
    }
    @GetMapping("/news")
    public List<NewsEntity>getAllNews(){
        return newsService.getAllNews();
    }
}
