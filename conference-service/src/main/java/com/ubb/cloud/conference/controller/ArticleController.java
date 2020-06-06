package com.ubb.cloud.conference.controller;

import com.ubb.cloud.conference.model.Article;
import com.ubb.cloud.conference.model.ArticleDTO;
import com.ubb.cloud.conference.model.ArticleDetailDTO;
import com.ubb.cloud.conference.model.UserDTO;
import com.ubb.cloud.conference.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/conference/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void saveArticle(@Valid @RequestBody Article article) {
        articleService.saveArticle(article);
    }

    @RequestMapping(value = "/domain/{domain}", method = RequestMethod.GET)
    public List<ArticleDTO> filterArticlesByDomain(@PathVariable String domain) {
        return articleService.getArticlesByDomain(domain);
    }

    @RequestMapping(value = "/author", method = RequestMethod.GET)
    public List<ArticleDTO> filterArticleByAuthor(@RequestParam("firstName") String firstName,
                                                  @RequestParam("lastName") String lastName) {
        UserDTO userDTO = new UserDTO(firstName, lastName);
        return articleService.getArticlesByAuthor(userDTO);
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public List<ArticleDTO> filterArticlesByDomainAndAuthor(@RequestParam("domain") String domain,
                                                            @RequestParam("firstName") String firstName,
                                                            @RequestParam("lastName") String lastName) {
        UserDTO userDTO = new UserDTO(firstName, lastName);
        return articleService.getArticlesByDomainAndAuthor(domain,userDTO);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<ArticleDTO> getArticles() {
        return articleService.getArticles();
    }

    @RequestMapping(value = "/unregistered", method = RequestMethod.GET)
    public List<ArticleDetailDTO> getArticlesNotRegistered() {
        return articleService.getArticlesWithNoTalks();
    }
}