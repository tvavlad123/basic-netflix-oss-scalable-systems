package com.ubb.cloud.conference.repository;

import com.ubb.cloud.conference.model.Article;
import com.ubb.cloud.conference.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    List<Article> findArticlesByDomain(String domain);

    List<Article> findArticlesByAuthor(UserDetails author);

    List<Article> findArticlesByDomainAndAuthor(String domain, UserDetails author);

    List<Article> findArticlesByTalksIsNull();

    Optional<Article> findById(Integer id);
}