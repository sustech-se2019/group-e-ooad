package com.threeNews.springproject.web;

import com.threeNews.springproject.domain.News;
import com.threeNews.springproject.domain.ResultVO;
import com.threeNews.springproject.domain.ThreeNews;
import com.threeNews.springproject.domain.User;
import com.threeNews.springproject.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for news center in springboot
 *
 * @author Jinhao Zhong
 * @version 1.0
 */
@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    /**
     * Get news according to news' id.
     *
     * @param request HttpServletRequest type, to get the user information in session.
     * @param newsId  news's id.
     * @return status code and news.
     */
    @ResponseBody
    @RequestMapping(value = "getNews", method = RequestMethod.POST)
    private ResultVO<News> getNews(HttpServletRequest request, @RequestParam String newsId) {
        Object thisClient = request.getSession().getAttribute("entity");
        User user = (User) thisClient;
        if (user == null) {
            News news = newsService.getById(Long.parseLong(newsId));
            return new ResultVO<>(0, "news_info", news);
        } else {
            News news = newsService.getById(Long.parseLong(newsId));
            int code = newsService.isLiked(user.getId(), Long.parseLong(newsId));
            code += 10 * newsService.isBookMark(user.getId(), Long.parseLong(newsId));
            return new ResultVO<>(code, "news_info", news);
        }
    }

    @ResponseBody
    @RequestMapping(value = "closeNews", method = RequestMethod.POST)
    private ResultVO<String> closeNews(HttpServletRequest request, @RequestParam String website, @RequestParam String rLikeStatus, @RequestParam String rBookmarkStatus, @RequestParam String newsId) {
        Object thisClient = request.getSession().getAttribute("entity");
        User user = (User) thisClient;
        if (user == null) {
            return new ResultVO<>(0, "Not login yet");
        } else {
            int likeStatus = Integer.parseInt(rLikeStatus);
            if (likeStatus == 1) {
                newsService.addLike(user.getId(), Long.parseLong(newsId));
            } else if (likeStatus == -1) {
                newsService.addDisLike(user.getId(), Long.parseLong(newsId));
            } else if (likeStatus == 0) {
                newsService.removeLike(user.getId(), Long.parseLong(newsId));
            }
            int bookmarkStatus = Integer.parseInt(rBookmarkStatus);
            if (bookmarkStatus == 1) {
                newsService.addBookMark(user.getId(), Long.parseLong(newsId));
            } else if (bookmarkStatus == 0) {
                newsService.removeBookMark(user.getId(), Long.parseLong(newsId));
            }
            return new ResultVO<>(1, "update user news link status", "Successfully");
        }
    }

    /**
     * To give recommend news to user, if user have not login yet, give random news.
     *
     * @param request The HttpServletRequest entity, use it to obtain the user entity in session.
     * @return News to app.
     */
    @ResponseBody
    @RequestMapping(value = "getRecommend", method = RequestMethod.POST)
    private ThreeNews getRecommend(HttpServletRequest request) {
        Object thisClient = request.getSession().getAttribute("entity");
        User user = (User) thisClient;
        int count = 0;
        News news;
        News[] newsList = new News[3];
        for (int i = 0; i < 3; i++) {
            if (user == null) {
                news = newsService.getRandomNews().get(0);
                Object historyObject = request.getSession().getAttribute("history");
                List<Long> historyList = (List<Long>) historyObject;
                if (historyList == null) {
                    System.out.println("History is not exist.");
                    historyList = new ArrayList<>();
                    historyList.add(news.getId());
                } else {
                    while (historyList.contains(news.getId()) && count < 10) {
                        System.out.println("news exist in history.");
                        news = newsService.getRandomNews().get(0);
                        count++;
                    }
                    historyList.add(news.getId());
                }
                newsList[i] = news;
                request.getSession().setAttribute("history", historyList);
            } else {
                news = newsService.getRandomNews().get(0);
                Object historyObject = request.getSession().getAttribute("history");
                List<Long> historyList = (List<Long>) historyObject;
                if (historyList == null) {
                    System.out.println("History is not exist.");
                    historyList = new ArrayList<>();
                    historyList.add(news.getId());
                } else {
                    while (historyList.contains(news.getId()) && count < 10) {
                        System.out.println("news exist in history.");
                        news = newsService.getRandomNews().get(0);
                        count++;
                    }
                    historyList.add(news.getId());
                }
                newsList[i] = news;
                request.getSession().setAttribute("history", historyList);
            }
        }

        return new ThreeNews(newsList[0], newsList[1], newsList[2]);
    }
}
