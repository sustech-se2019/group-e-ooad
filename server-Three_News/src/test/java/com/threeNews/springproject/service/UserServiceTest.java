package com.threeNews.springproject.service;

import com.threeNews.springproject.api.UserRepository;
import com.threeNews.springproject.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
/**
 *
 * UserServiceTest
 * @author Jinxuan Liu
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    /**
     *
     * test checkPassword() with null
     */
    @Test
    public void testCheckPasswordWithNull() {
        assertEquals(userService.checkPassword(null,null),false);
    }

    /**
     *
     * test checkPassword() with correct input
     */
    @Test
    public void testCheckPasswordWithValid() {
        assertEquals(userService.checkPassword("11611129","11111111"),true);
    }

    /**
     *
     * test checkPassword() with inValid input
     */
    @Test
    public void testCheckPasswordWithInValid() {
        assertEquals(userService.checkPassword("11611129","111111"),false);
    }

    /**
     *
     * test checkName() with Null
     */
    @Test
    public void testCheckNameWithNone() {
        assertEquals(userService.checkName("asldkfjasldfjasdlf"),false);
    }

    /**
     *
     * test checkName() with correct(exist)
     */
    @Test
    public void testCheckNameWithExist() {
        assertEquals(userService.checkName("11611129"),true);
    }

    /**
     *
     * test getByName() With not exist
     */
    @Test
    public void getByNameWithNotExist() {
        assertEquals(userService.checkName("a11611129"),false);
    }

    /**
     *
     * test getByName() With exist
     */
    @Test
    public void getByNameWithExist() {
        assertEquals(userService.getByName("11611129").getName(),userRepository.findByName("11611129").get(0).getName());
    }

    /**
     *
     * test addUser() with Correct
     */
    @Test
    public void addUserWithCorrect() {
        userService.addUser(new User("99998888","11112222"));
        assertEquals(userRepository.findByName("99998888").isEmpty(),false);
    }
    /**
     *
     * test addUser() with 1111
     */
    @Test
    public void addUserWithOther() {
        userService.addUser(new User("1111","11112222"));
        assertEquals(userRepository.findByName("1111").isEmpty(),false);
    }

}