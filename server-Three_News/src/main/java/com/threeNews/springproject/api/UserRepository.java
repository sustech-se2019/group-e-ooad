package com.threeNews.springproject.api;

import com.threeNews.springproject.domain.User;

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
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from user where user_name=?1 and user_password=?2", nativeQuery = true)
    List<User> findByNameAndPassword(String name, String password);

    @Query(value = "select * from user where user_name=?1", nativeQuery = true)
    List<User> findByName(String name);

    @Query(value = "select * from news n join user_liked ul on n.news_id=ul.news_id where ul.user_id=?1", nativeQuery = true)
    List<User> findLikedById(long id);

    @Query(value = "update user set nick_name=?1,occupation=?2,birthday=?3,gender=?4,age=?5 where user_id=?6", nativeQuery = true)
    @Modifying
    void updateNickName(String name, String occupation, String birthday, int gender, int age, long id);

}
