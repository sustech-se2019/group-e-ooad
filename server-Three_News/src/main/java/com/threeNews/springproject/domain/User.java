package com.threeNews.springproject.domain;

import javax.persistence.*;

/**
 * A class of user entity
 *
 * @author Jinxuan Liu
 * @author Jinhao Zhong
 * @version 1.0
 */
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private long id;
    @Column(name="user_name")
    private String name;
    @Column(name="user_password")
    private String password;
    @Column(name = "nick_name")
    private String nickname;
    @Column(name = "age")
    private int age;
    @Column(name = "occupation")
    private String occupation;
    @Column(name = "gender")
    private int gender;
    /**
     *
     * @param name username
     * @param password password
     */
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, String nickname, int age, String occupation, int gender) {
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
        this.occupation = occupation;
        this.gender = gender;
    }

    public User(){}

    /**
     * Username setter
     *
     * @param name username
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Username getter
     *
     * @return name username
     */
    public String getName() {
        return name;
    }

    /**
     * Id getter
     *
     * @return id user id
     */
    public long getId() {
        return id;
    }

    /**
     * id setter
     *
     * @param id user id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * password getter
     *
     * @return password user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * password setter
     *
     * @param password user password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * nickname getter
     *
     * @return nickname user nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * nickname setter
     *
     * @param nickname username nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
