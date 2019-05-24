package com.threeNews.springproject.domain;

/**
 * Data Return in response.
 *
 * @author Jinhao Zhong
 * @version 1.0
 */

public class ThreeNews {

    private long id1;

    private String kind1;

    private String url1;

    private String title1;

    private String introduction1;

    private long id2;

    private String kind2;

    private String url2;

    private String title2;

    private String introduction2;

    private long id3;

    private String kind3;

    private String url3;

    private String title3;

    private String introduction3;

    public ThreeNews() {
    }

    public ThreeNews(News news1, News news2, News news3) {
        this.id1 = news1.getId();
        this.kind1 = news1.getLabel();
        this.url1 = news1.getUrl();
        this.title1 = news1.getTitle();
        this.introduction1 = news1.getIntroduction();
        this.id2 = news2.getId();
        this.kind2 = news2.getLabel();
        this.url2 = news2.getUrl();
        this.title2 = news2.getTitle();
        this.introduction2 = news2.getIntroduction();
        this.id3 = news3.getId();
        this.kind3 = news3.getLabel();
        this.url3 = news3.getUrl();
        this.title3 = news3.getTitle();
        this.introduction3 = news3.getIntroduction();
    }

    public long getId1() {
        return id1;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }

    public String getKind1() {
        return kind1;
    }

    public void setKind1(String kind1) {
        this.kind1 = kind1;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getIntroduction1() {
        return introduction1;
    }

    public void setIntroduction1(String introduction1) {
        this.introduction1 = introduction1;
    }

    public long getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }

    public String getKind2() {
        return kind2;
    }

    public void setKind2(String kind2) {
        this.kind2 = kind2;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getIntroduction2() {
        return introduction2;
    }

    public void setIntroduction2(String introduction2) {
        this.introduction2 = introduction2;
    }

    public long getId3() {
        return id3;
    }

    public void setId3(int id3) {
        this.id3 = id3;
    }

    public String getKind3() {
        return kind3;
    }

    public void setKind3(String kind3) {
        this.kind3 = kind3;
    }

    public String getUrl3() {
        return url3;
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
    }

    public String getTitle3() {
        return title3;
    }

    public void setTitle3(String title3) {
        this.title3 = title3;
    }

    public String getIntroduction3() {
        return introduction3;
    }

    public void setIntroduction3(String introduction3) {
        this.introduction3 = introduction3;
    }
}
