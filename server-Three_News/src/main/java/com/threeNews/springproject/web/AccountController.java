package com.threeNews.springproject.web;

import com.threeNews.springproject.domain.ResultVO;
import com.threeNews.springproject.domain.User;
import com.threeNews.springproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller for user account center in springboot
 *
 * @author Jinhao Zhong
 * @version 1.0
 */
@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    /**
     * A login post receiver,
     *
     * @param username username
     * @param password password
     * @return Json String to requester
     */
    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    private ResultVO<User> login(HttpServletRequest request, @RequestParam String username, @RequestParam String password) {
        System.out.println(username);
        System.out.println(password);
        int code = 0;
        User user = null;
        String msg = null;
        if (userService.checkPassword(username, password)) {
            user = userService.getByName(username);
            code = 1;
            msg = "Login successfully";
        }
        // code = -1 wrong password
        // code = -2 username invalid
        else {
            if (userService.checkName(username)) {
                code = -1;
                msg = "Wrong password";
            } else {
                code = -2;
                msg = "Invalid username";
            }
        }
        request.getSession().setAttribute("entity", user);
        return new ResultVO<>(code, msg, user);
    }

    /**
     * A signup post receiver
     *
     * @param username username
     * @param password password
     * @return Json String to requester
     */
    @ResponseBody
    @RequestMapping(value = "signup", method = RequestMethod.POST)
    private ResultVO<User> signup(@RequestParam String username, @RequestParam String password) {
        System.out.println(username);
        System.out.println(password);
        int code = 0;
        User user = null;
        String msg = null;
        if (userService.checkName(username)) {
            code = -1;
            msg = "Signup fail";
        } else {
            user = new User(username, password);
            userService.addUser(user);
            code = 1;
            msg = "Signup successfully";
        }
        return new ResultVO<>(code, msg, user);
    }

    /**
     * To allow user sign up.
     *
     * @param username   Account username
     * @param password   User's password
     * @param age        User's age
     * @param occupation User's occupation
     * @param gender     User's gender
     * @param nickname   User's nickname
     * @return Status of post
     */
    @ResponseBody
    @RequestMapping(value = "signUp", method = RequestMethod.POST)
    private ResultVO<User> signUp(@RequestParam String username, @RequestParam String password, @RequestParam String age, @RequestParam String occupation, @RequestParam String gender, @RequestParam String nickname) {
        int code = 0;
        User user = null;
        String msg = null;
        if (userService.checkName(username)) {
            code = -1;
            msg = "Signup fail";
        } else {
            user = new User(username, password, nickname, Integer.parseInt(age), occupation, Integer.parseInt(gender));
            userService.addUser(user);
            code = 1;
            msg = "Signup successfully";
        }
        return new ResultVO<>(code, msg, user);
    }

    /**
     * @param request The HttpServletRequest entity, use it to obtain the user entity in session.
     * @return code status which is state whether succeed or not.
     */
    @ResponseBody
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    private ResultVO<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("entity");
        request.getSession().removeAttribute("history");
        return new ResultVO<>(1, "Log out successfully", "User log out");
    }

    /**
     * update information
     *
     * @param request    Http request
     * @param occupation user's occupation
     * @param birthday   user's birthday
     * @param genderString     user's gender
     * @param name       user's nick name
     * @return status code
     */
    @ResponseBody
    @RequestMapping(value = "updateInformation", method = RequestMethod.POST)
    private ResultVO<String> update(HttpServletRequest request, @RequestParam String occupation, @RequestParam String birthday, @RequestParam String genderString, @RequestParam String name) {
        Object thisClient = request.getSession().getAttribute("entity");
        User user = (User) thisClient;
        if (user == null) {
            return new ResultVO<>(0, "Not login yet");
        } else {
            int gender = -1;
            if (genderString.equals("男")) {
                gender = 1;
            } else
                gender = 0;
            userService.update(name, occupation, birthday, gender, Integer.parseInt(String.valueOf(user.getId())));
            return new ResultVO<>(1, "update information status", "Successfully");
        }
    }
}
