package com.threeNews.springproject.domain;

import org.junit.Test;

import static org.junit.Assert.*;
/**
 * ResultVOTest
 * @author Jinxuan Liu
 * @version 1.0
 */
public class ResultVOTest {
    /**
     *
     * testGetCode() with 0
     */
    @Test
    public void testGetCodeWith0() {
        int code = 0;
        String msg ="test";
        ResultVO<User> resultVO=new ResultVO<User>(code, msg);
        assertEquals((int)resultVO.getCode(),0);
    }
    /**
     *
     * testGetCode() with Integer.MAX_VALUE
     */
    @Test
    public void testGetCodeWithLargeNum() {
        int code = Integer.MAX_VALUE;
        String msg ="test";
        ResultVO<User> resultVO=new ResultVO<User>(code, msg);
        assertEquals((int)resultVO.getCode(),Integer.MAX_VALUE);
    }
    /**
     *
     * test setCode() with 0
     */
    @Test
    public void testSetCodeWith0() {
        int code = Integer.MAX_VALUE;
        String msg ="test";
        ResultVO<User> resultVO=new ResultVO<User>(code, msg);
        resultVO.setCode(0);
        assertEquals((int)resultVO.getCode(),0);
    }
    /**
     *
     * test setCode() with Integer.MAX_VALUE
     */
    @Test
    public void testSetCodeWithLargeNum() {
        int code = 0;
        String msg ="test";
        ResultVO<User> resultVO=new ResultVO<User>(code, msg);
        resultVO.setCode(Integer.MAX_VALUE);
        assertEquals((int)resultVO.getCode(),Integer.MAX_VALUE);
    }
    /**
     *
     * test getMessage() with Null
     */
    @Test
    public void testGetMessageWithNull() {
        int code = 0;
        String msg =null;
        ResultVO<User> resultVO=new ResultVO<User>(code, msg);
        assertEquals(resultVO.getMessage(),null);
    }

    /**
     *
     * test getMessage() with correct
     */
    @Test
    public void testGetMessageWithCorrect() {
        int code = 0;
        String msg ="111";
        ResultVO<User> resultVO=new ResultVO<User>(code, msg);
        assertEquals(resultVO.getMessage(),"111");
    }
    /**
     *
     * test setMessage() with Null
     */
    @Test
    public void testSetMessageNull() {
        int code = 0;
        String msg ="111";
        ResultVO<User> resultVO=new ResultVO<User>(code, msg);
        resultVO.setMessage(null);
        assertEquals(resultVO.getMessage(),null);
    }
    /**
     *
     * test setMessage() with correct input
     */
    @Test
    public void testSetMessageCorrect() {
        int code = 0;
        String msg =null;
        ResultVO<User> resultVO=new ResultVO<User>(code, msg);
        resultVO.setMessage("111");
        assertEquals(resultVO.getMessage(),"111");
    }
    /**
     *
     * test getData() with Null
     */
    @Test
    public void testGetDataWithNull() {
        int code = 0;
        String msg ="111";
        ResultVO<User> resultVO=new ResultVO<User>(code, msg);
        resultVO.setData(null);
        assertEquals(resultVO.getData(),null);
    }
    /**
     *
     * test getData() with Null
     */
    @Test
    public void testGetDataWithCorrect() {
        int code = 0;
        String msg ="111";
        ResultVO<User> resultVO=new ResultVO<User>(code, msg);
        User user = new User("111","111");
        resultVO.setData(user);
        assertEquals(resultVO.getData(),user);
    }
    /**
     *
     * test setData() with Null
     */
    @Test
    public void testSetDataWithNull() {
        int code = 0;
        String msg ="111";
        ResultVO<User> resultVO=new ResultVO<User>(code, msg);
        resultVO.setData(null);
        assertEquals(resultVO.getData(),null);
    }
    /**
     *
     * test setData() with correct input
     */
    @Test
    public void testSetDataWithCorrect() {
        int code = 0;
        String msg =null;
        ResultVO<User> resultVO=new ResultVO<User>(code, msg);
        User user = new User("111","111");
        resultVO.setData(user);
        assertEquals(resultVO.getData(),user);
    }
}