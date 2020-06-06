package com.ubb.cloud.conference.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "articles", schema = "conference")
public class Article {
    private Integer id;
    private String title;
    private UserDetails author;
    private String domain;
    private String description;
    private String link;
    private Set<Talk> talks;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 100)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public UserDetails getAuthor() {
        return author;
    }

    public void setAuthor(UserDetails author) {
        this.author = author;
    }

    @Basic
    @Column(name = "domain", nullable = true, length = 50)
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 500)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "link", nullable = true, length = 200)
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @OneToMany(mappedBy="article")
    public Set<Talk> getTalks() {
        return talks;
    }

    public void setTalks(Set<Talk> talks) {
        this.talks = talks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return Objects.equals(getId(), article.getId()) &&
                Objects.equals(getTitle(), article.getTitle()) &&
                Objects.equals(getAuthor(), article.getAuthor()) &&
                Objects.equals(getDomain(), article.getDomain()) &&
                Objects.equals(getDescription(), article.getDescription()) &&
                Objects.equals(getLink(), article.getLink()) &&
                Objects.equals(getTalks(), article.getTalks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getAuthor(), getDomain(), getDescription(), getLink(), getTalks());
    }

    public ArticleDTO toArticleDTO(){
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(getId());
        articleDTO.setTitle(getTitle());
        articleDTO.setDomain(getDomain());
        articleDTO.setAuthor(getAuthor().toUserDTO());
        return articleDTO;
    }

    public ArticleDetailDTO toArticleDetailDTO() {
        ArticleDetailDTO articleDetailDTO = new ArticleDetailDTO();
        articleDetailDTO.setId(getId());
        articleDetailDTO.setTitle(getTitle());
        articleDetailDTO.setDomain(getDomain());
        articleDetailDTO.setDescription(getDescription());
        articleDetailDTO.setAuthor(getAuthor().toUserDTO());
        return articleDetailDTO;
    }
}