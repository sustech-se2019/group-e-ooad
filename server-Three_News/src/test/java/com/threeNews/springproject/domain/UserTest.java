package com.threeNews.springproject.domain;

import org.junit.Test;

import static org.junit.Assert.*;
/**
 *
 * UserTest
 * @author Jinxuan Liu
 * @version 1.0
 */
public class UserTest {
    /**
     *
     * test getName() With input "JiXuan"
     */
    @Test
    public void testGetNameWithJiXuan() {
        User user = new User("JiXuan","11111111");
        assertEquals(user.getName(),"JiXuan");
    }
    /**
     *
     * test getName() With input "Jinhao"
     */
    @Test
    public void testGetNameWithJinHao() {
        User user = new User("JinHao","11111111");
        assertEquals(user.getName(),"JinHao");
    }

}