package com.ubb.cloud.conference.service;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.ubb.cloud.conference.model.Article;
import com.ubb.cloud.conference.model.ArticleDTO;
import com.ubb.cloud.conference.model.ArticleDetailDTO;
import com.ubb.cloud.conference.model.UserDTO;
import com.ubb.cloud.conference.model.UserDetails;
import com.ubb.cloud.conference.repository.ArticleRepository;
import com.ubb.cloud.conference.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, UserDetailsRepository userDetailsRepository) {
        this.articleRepository = articleRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    public List<ArticleDTO> getArticlesByDomain(String domain) {
        return articleRepository.findArticlesByDomain(domain).stream().map(Article::toArticleDTO)
                .collect(Collectors.toList());
    }

    public List<ArticleDTO> getArticlesByAuthor(UserDTO userDTO) {
        UserDetails userDetails = userDetailsRepository.findUserDetailsByFirstNameAndLastName(userDTO.getFirstName(), userDTO.getLastName());
        return articleRepository.findArticlesByAuthor(userDetails).stream().map(Article::toArticleDTO)
                .collect(Collectors.toList());
    }

    public List<ArticleDTO> getArticles() {
        return articleRepository.findAll().stream().map(Article::toArticleDTO).collect(Collectors.toList());
    }

    public List<ArticleDTO> getArticlesByDomainAndAuthor(String domain, UserDTO author) {
        UserDetails userDetails = userDetailsRepository.findUserDetailsByFirstNameAndLastName(author.getFirstName(), author.getLastName());
        return articleRepository.findArticlesByDomainAndAuthor(domain, userDetails).stream().map(Article::toArticleDTO)
                .collect(Collectors.toList());
    }

    public List<ArticleDetailDTO> getArticlesWithNoTalks() {
        List<Article> articles = articleRepository.findArticlesByTalksIsNull();
        return articles.stream().map(Article::toArticleDetailDTO)
                .collect(Collectors.toList());
    }

    public void saveArticle(Article article) {
        articleRepository.save(article);
    }
}