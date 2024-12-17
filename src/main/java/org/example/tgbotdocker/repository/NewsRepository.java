package org.example.tgbotdocker.repository;

import org.example.tgbotdocker.model.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
}
