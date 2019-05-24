package com.threeNews.springproject.domain;

import javax.persistence.*;

/**
 * A class of News entity
 *
 * @author Jinxuan Liu
 * @author Sen Peng
 * @version 1.0
 */
@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "news_id")
    private long id;
    @Column(name = "news_title")
    private String title;
    @Column(name = "news_url")
    private String url;
    @Column(name = "label")
    private String label;
    @Column(name = "introduction")
    private String introduction;

    /**
     * @param title title
     * @param url   url
     */
    public News(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public News() {
    }

    /**
     * title setter
     *
     * @param title news title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * title getter
     *
     * @return title news title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Id getter
     *
     * @return id news id
     */
    public long getId() {
        return id;
    }

    /**
     * id setter
     *
     * @param id news id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * url getter
     *
     * @return url news url
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * url setter
     *
     * @param url news url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
