package com.threeNews.springproject.service;

import com.threeNews.springproject.api.UserRepository;
import com.threeNews.springproject.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Table;
import java.util.List;

/**
 * Service for user in springboot
 *
 * @author Jinxuan Liu
 * @author Jinhao Zhong
 * @version 1.0
 */
@RestController
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * A method to check if the user enter correct identification
     *
     * @param username       username which the client input
     * @param input_password password which the client input
     * @return boolean type to recognize if it is correct
     */
    public boolean checkPassword(String username, String input_password) {
        List<User> userList = userRepository.findByNameAndPassword(username, input_password);
        if (userList.size() == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * check whether username is exist
     *
     * @param username username
     * @return boolean type to recognize whether it is exist
     */
    public boolean checkName(String username) {
        List<User> userList = userRepository.findByName(username);
        if (userList.size() == 1) {
            return true;
        } else
            return false;
    }

    /**
     * get user by username
     *
     * @param name username
     * @return user object
     */
    public User getByName(String name) {
        List<User> userList = userRepository.findByName(name);
//        if (!userList.isEmpty())
//            return null;
        return userList.get(0);
    }

    /**
     * register a user
     *
     * @param user user object
     */
    public void addUser(User user) {
        userRepository.save(user);
    }

    /**
     * Update information.
     *
     * @param name nickname
     * @param occupation occupation of user
     * @param birthday user's birthday
     * @param gender user's gender
     * @param id user's id
     */
    @Transactional
    public void update(String name,String occupation, String birthday, int gender, int id){
        int age = 2019 - Integer.parseInt(birthday);
        userRepository.updateNickName(name,occupation,birthday,gender, age ,id);
    }
}
