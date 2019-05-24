package com.threeNews.springproject.api;

import com.threeNews.springproject.domain.News;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * The repository class in springboot to execute sql
 *
 * @author Jinhao Zhong
 * @version 1.0
 */
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query(value = "select * from news where news_title=?1", nativeQuery = true)
    List<News> findByName(String name);

    @Query(value = "select * from news where news_title like '%?%'", nativeQuery = true)
    List<News> search(String name);

    @Query(value = "select * from news order by rand() LIMIT 1", nativeQuery = true)
    List<News> randomNews();

    @Query(value = "select * from news where news_id=?1", nativeQuery = true)
    List<News> findById(long id);

    @Query(value = "select * from news n join user_liked ul on n.news_id=ul.news_id where ul.user_id=?1", nativeQuery = true)
    List<News> findLikedById(long id);

    @Query(value = "select isLiked from user_liked where user_id=?1 and news_id=?2", nativeQuery = true)
    List<Long> isLikedById(long id, long news_id);

    @Query(value = "select isBookmark from user_bookmark where user_id=?1 and news_id=?2", nativeQuery = true)
    List<Long> isBookMarkById(long id, long news_id);

    @Modifying
    @Query(value = "insert into user_liked (user_id,news_id,isLiked) values (?,?,?)", nativeQuery = true)
    void addLiked(long userId, long newsId, long code);

    @Modifying
    @Query(value = "insert into user_liked (user_id,news_id,isLiked) values (?,?,?)", nativeQuery = true)
    void addDisLiked(long userId, long newsId, long code);

    @Modifying
    @Query(value = "delete from user_liked where user_id=?1 and news_id=?2", nativeQuery = true)
    void removeliked(long userId, long newsId);

    @Modifying
    @Query(value = "insert into user_bookmark (user_id,news_id,isBookmark) values (?1,?2,1)", nativeQuery = true)
    void addBookMark(long userId, long newsId);

    @Modifying
    @Query(value = "delete from user_bookmark where user_id=?1 and news_id=?2", nativeQuery = true)
    void removeBookMark(long userId, long newsId);

    @Query(value = "update user_liked set isLiked=?1 where user_id=?2 and news_id=?3", nativeQuery = true)
    @Modifying
    void updateLiked(long code, long id, long news_id);

}
