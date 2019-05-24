package com.threeNews.springproject.service;

import com.threeNews.springproject.api.NewsRepository;
import com.threeNews.springproject.domain.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Service for news in springboot
 *
 * @author Jinxuan Liu
 * @author Jinhao Zhong
 * @version 1.0
 */
@RestController
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    /**
     * get news by news title
     *
     * @param title username
     * @return news object
     */
    public News getByName(String title) {
        List<News> newsList = newsRepository.findByName(title);
        return newsList.get(0);
    }

    /**
     * get news by news id
     *
     * @param id news id
     * @return news object
     */
    public News getById(long id) {
        List<News> newsList = newsRepository.findById(id);
        if (!newsList.isEmpty()) {
            return newsList.get(0);
        } else
            return null;
    }

    /**
     * get news whether liked
     *
     * @param userId user's id
     * @param newsId news' id
     * @return status code
     */
    public int isLiked(long userId, long newsId) {
        List<Long> newsList = newsRepository.isLikedById(userId, newsId);
        if (!newsList.isEmpty())
            return (int) Integer.parseInt(String.valueOf(newsList.get(0)));
        else
            return 0;
    }

    /**
     * give news the user likes
     * @param id user's id
     * @return news
     */
    public News giveLike(long id){
        List<News> newsList = newsRepository.giveLiked(id);
        if (newsList.isEmpty()){
            return newsRepository.randomNews().get(0);
        }
        return newsRepository.giveLiked(id).get(0);
    }
    /**
     * get news whether bookmark
     *
     * @param userId user's id
     * @param newsId news' id
     * @return status code
     */
    public int isBookMark(long userId, long newsId) {
        List<Long> newsList = newsRepository.isBookMarkById(userId, newsId);
        if (!newsList.isEmpty())
            return (int) Integer.parseInt(String.valueOf(newsList.get(0)));
        else
            return 0;
    }

    /**
     * Add like to user
     *
     * @param userId user's id
     * @param newsId news' id
     */
    @Transactional
    public void addLike(long userId, long newsId) {
        List<Long> newsList = newsRepository.isLikedById(userId, newsId);
        if (!newsList.isEmpty()) {
            newsRepository.updateLiked(1, userId, newsId);
        } else
            newsRepository.addLiked(userId, newsId, 1);
    }

    /**
     * Add disLike to user
     *
     * @param userId user's id
     * @param newsId news' id
     */
    @Transactional
    public void addDisLike(long userId, long newsId) {
        List<Long> newsList = newsRepository.isLikedById(userId, newsId);
        if (!newsList.isEmpty()) {
            newsRepository.updateLiked(-1, userId, newsId);
        } else
            newsRepository.addDisLiked(userId, newsId, -1);
    }

    /**
     * Add bookmark to user
     *
     * @param userId user's id
     * @param newsId news' id
     */
    @Transactional
    public void addBookMark(long userId, long newsId) {
        List<Long> newsList = newsRepository.isBookMarkById(userId, newsId);
        if (newsList.isEmpty()) {
            newsRepository.addBookMark(userId, newsId);
        }
    }

    /**
     * remove like for user
     *
     * @param userId user's id
     * @param newsId news' id
     */
    @Transactional
    public void removeLike(long userId, long newsId) {
        List<Long> newsList = newsRepository.isLikedById(userId, newsId);
        if (!newsList.isEmpty()) {
            newsRepository.removeliked(userId, newsId);
        }
    }

    /**
     * remove bookmark for user
     *
     * @param userId user's id
     * @param newsId news' id
     */
    @Transactional
    public void removeBookMark(long userId, long newsId) {
        List<Long> newsList = newsRepository.isBookMarkById(userId, newsId);
        if (!newsList.isEmpty()) {
            newsRepository.removeBookMark(userId, newsId);
        }
    }

    /**
     * add a news
     *
     * @param news news object
     */
    public void addNews(News news) {
        newsRepository.save(news);
    }

    public News getRandomNews() {
        return newsRepository.randomNews().get(0);
    }
}
